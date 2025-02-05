package com.example.specime.firestore

import android.net.Uri
import com.example.specime.screens.notifications.Notification
import com.example.specime.screens.notifications.NotificationType
import com.example.specime.screens.test.Answer
import com.example.specime.screens.test.Question
import com.example.specime.userrepository.UserRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FireStoreController @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val userRepository: UserRepository,
) {
    fun searchUsersByDisplayName(
        query: String,
        callback: (success: Boolean, users: List<Map<String, Any>>?) -> Unit
    ) {
        if (query.isBlank()) {
            callback(true, emptyList())
            return
        }

        val usersCollection = fireStore.collection("users")

        usersCollection
            .orderBy("displayName")
            .whereNotEqualTo(FieldPath.documentId(), userRepository.getUserId())
            .startAt(query)
            .endAt(query + "\uf8ff")
            .get()
            .addOnSuccessListener { result ->
                val users = result.documents.map { document ->
                    val data = document.data ?: emptyMap<String, Any>()
                    data + ("userId" to document.id)
                }
                callback(true, users)
            }
    }

    fun fetchNotifications(callback: (success: Boolean, notifications: List<Notification>) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, emptyList())
        val notificationsRef = fireStore.collection("users").document(userId).collection("notifications")

        notificationsRef.get()
            .addOnSuccessListener { querySnapshot ->
                val notificationDocuments = querySnapshot.documents

                if (notificationDocuments.isEmpty()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val notifications = mutableListOf<Notification>()
                var processedCount = 0

                notificationDocuments.forEach { document ->
                    val data = document.data

                    if (data != null) {
                        val relatedUserId = data["relatedUserId"] as? String ?: return@forEach
                        val type = NotificationType.valueOf(data["type"] as? String ?: return@forEach)
                        val timestamp = (data["timestamp"] as? Long) ?: return@forEach

                        fireStore.collection("users").document(relatedUserId).get()
                            .addOnSuccessListener { userDocument ->
                                val displayName = userDocument.getString("displayName") ?: "Unknown"
                                val profilePictureUrl = userDocument.getString("profilePictureUrl") ?: ""
                                val notification = Notification(
                                    relatedUserId = relatedUserId,
                                    displayName = displayName,
                                    profilePictureUrl = profilePictureUrl,
                                    type = type,
                                    timestamp = timestamp
                                )
                                notifications.add(notification)
                            }
                            .addOnCompleteListener {
                                processedCount++
                                if (processedCount == notificationDocuments.size) {
                                    callback(true, notifications)
                                }
                            }
                    } else {
                        processedCount++
                        if (processedCount == notificationDocuments.size) {
                            callback(true, notifications)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false, emptyList())
            }
    }

    fun fetchFriends(callback: (success: Boolean, friends: List<Map<String, Any>>?) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, null)

        fireStore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val friendIds = (document.get("friends") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                if (friendIds.isEmpty()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val friendsCollection = fireStore.collection("users")
                val batches = friendIds.chunked(10)
                val friendsList = mutableListOf<Map<String, Any>>()
                var completedBatches = 0

                batches.forEach { batch ->
                    friendsCollection.whereIn(FieldPath.documentId(), batch)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            querySnapshot.documents.forEach { doc ->
                                val data = doc.data ?: emptyMap<String, Any>()
                                friendsList.add(data + ("userId" to doc.id))
                            }
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(true, friendsList)
                            }
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(true, friendsList)
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(false, null)
            }
    }

    fun fetchUserRelationships(
        callback: (success: Boolean, friends: List<String>?, pendings: List<String>?, requests: List<String>?) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return

        fireStore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val friends = (document.get("friends") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                    val pendings = (document.get("pendings") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                    val requests = (document.get("requests") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

                    callback(true, friends, pendings, requests)
                }
            }
    }

    fun sendFriendRequest(
        recipientId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userRef = fireStore.collection("users").document(userId)
        val recipientRef = fireStore.collection("users").document(recipientId)
        val recipientNotificationsRef = recipientRef.collection("notifications").document()

        val notificationData = mapOf(
            "relatedUserId" to userId,
            "type" to NotificationType.FRIEND_REQUEST.name,
            "timestamp" to System.currentTimeMillis()
        )

        fireStore.runBatch { batch ->
            batch.update(userRef, "requests", FieldValue.arrayUnion(recipientId))
            batch.update(recipientRef, "pendings", FieldValue.arrayUnion(userId))
            batch.set(recipientNotificationsRef, notificationData)
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
            callback(false)
        }
    }

    fun acceptFriendRequest(
        senderId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val senderRef = fireStore.collection("users").document(senderId)
        val userRef = fireStore.collection("users").document(userId)
        val senderNotificationsRef = senderRef.collection("notifications").document()
        val userNotificationsRef = userRef.collection("notifications")

        val notificationData = mapOf(
            "relatedUserId" to userId,
            "type" to NotificationType.FRIEND_REQUEST_ACCEPTED.name,
            "timestamp" to System.currentTimeMillis()
        )

        userNotificationsRef
            .whereEqualTo("relatedUserId", senderId)
            .whereEqualTo("type", NotificationType.FRIEND_REQUEST.name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                fireStore.runBatch { batch ->
                    batch.update(userRef, "pendings", FieldValue.arrayRemove(senderId))
                    batch.update(senderRef, "requests", FieldValue.arrayRemove(userId))
                    batch.update(userRef, "friends", FieldValue.arrayUnion(senderId))
                    batch.update(senderRef, "friends", FieldValue.arrayUnion(userId))
                    batch.set(senderNotificationsRef, notificationData)

                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                }.addOnSuccessListener {
                    callback(true)
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun rejectFriendRequest(
        senderId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userRef = fireStore.collection("users").document(userId)
        val senderRef = fireStore.collection("users").document(senderId)
        val userNotificationsRef = userRef.collection("notifications")

        userNotificationsRef
            .whereEqualTo("relatedUserId", senderId)
            .whereEqualTo("type", "FRIEND_REQUEST")
            .get()
            .addOnSuccessListener { querySnapshot ->
                fireStore.runBatch { batch ->
                    batch.update(userRef, "pendings", FieldValue.arrayRemove(senderId))
                    batch.update(senderRef, "requests", FieldValue.arrayRemove(userId))

                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                }.addOnSuccessListener {
                    callback(true)
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun removeFriend(
        friendId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userRef = fireStore.collection("users").document(userId)
        val friendRef = fireStore.collection("users").document(friendId)

        fireStore.runBatch { batch ->
            batch.update(userRef, "friends", FieldValue.arrayRemove(friendId))
            batch.update(friendRef, "friends", FieldValue.arrayRemove(userId))
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
            callback(false)
        }
    }

    fun cancelFriendRequest(
        recipientId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userRef = fireStore.collection("users").document(userId)
        val recipientRef = fireStore.collection("users").document(recipientId)
        val recipientNotificationsRef = recipientRef.collection("notifications")

        recipientNotificationsRef
            .whereEqualTo("relatedUserId", userId)
            .whereEqualTo("type", NotificationType.FRIEND_REQUEST.name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                fireStore.runBatch { batch ->
                    batch.update(userRef, "requests", FieldValue.arrayRemove(recipientId))
                    batch.update(recipientRef, "pendings", FieldValue.arrayRemove(userId))
                    for (document in querySnapshot.documents) {
                        batch.delete(document.reference)
                    }
                }.addOnSuccessListener {
                    callback(true)
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun fetchQuestions(callback: (questions: List<Question>?, error: String?) -> Unit) {
        fireStore.collection("questions")
            .get()
            .addOnSuccessListener { result ->
                val questions = result.documents.mapNotNull { document ->
                    val question = document.getString("question") ?: return@mapNotNull null
                    val answers = (document.get("answers") as? List<*>)?.mapNotNull { answer ->
                        (answer as? Map<*, *>)?.let { map ->
                            val label = map["label"] as? String ?: return@let null
                            val description = map["description"] as? String ?: return@let null
                            Answer(label, description)
                        }
                    } ?: emptyList()
                    Question(
                        question = question,
                        answers = answers.shuffled()
                    )
                }
                callback(questions, null)
            }
    }

    fun uploadTestResult(
        userId: String,
        data: Map<String, Any>,
    ) {
        val mutableData = data.toMutableMap()

        @Suppress("UNCHECKED_CAST")
        val result = data["result"] as? Map<String, Int>
        if (result != null) {
            val personality = determineDISCCombination(result)
            mutableData["personality"] = personality
        }

        fireStore.collection("test_results")
            .document(userId)
            .set(mutableData)
    }

    fun fetchUserResult(callback: (result: Map<String, Int>?, personality: String?, error: String?) -> Unit) {
        val userId = userRepository.getUserId()
        if (userId == null) {
            callback(null, null, "User not logged in")
            return
        }

        fireStore.collection("test_results")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val result = document.get("result") as? Map<*, *>
                    val parsedResult = result?.mapNotNull { (key, value) ->
                        val keyStr = key as? String
                        val valueInt = (value as? Long)?.toInt()
                        if (keyStr != null && valueInt != null) keyStr to valueInt else null
                    }?.toMap()

                    val personality = document.getString("personality")

                    callback(parsedResult, personality, null)
                } else {
                    callback(emptyMap(), null, null)
                }
            }
            .addOnFailureListener { exception ->
                callback(null, null, exception.localizedMessage)
            }
    }

    private fun saveProfileImageUrl(
        downloadUrl: String,
        callback: (Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId()
        if (userId == null) {
            callback(false)
            return
        }

        val userDocRef = fireStore.collection("users").document(userId)

        userDocRef.set(mapOf("profilePictureUrl" to downloadUrl), SetOptions.merge())
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun uploadProfilePicture(
        uri: Uri,
        callback: (Boolean, String?) -> Unit
    ) {
        val userId = userRepository.getUserId()
        if (userId == null) {
            callback(false, null)
            return
        }

        val storageReference =
            FirebaseStorage.getInstance().reference.child("profile_pictures/$userId.jpg")

        storageReference.putFile(uri)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                    saveProfileImageUrl(downloadUrl.toString()) { success ->
                        if (success) {
                            callback(true, downloadUrl.toString())
                        } else {
                            callback(false, null)
                        }
                    }
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    callback(false, null)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false, null)
            }
    }

    fun uploadUserBirthday(
        birthday: String,
        callback: (Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId()
        if (userId == null) {
            callback(false)
            return
        }

        val userDocRef = fireStore.collection("users").document(userId)

        userDocRef.set(mapOf("birthday" to birthday), SetOptions.merge())
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
    }

    fun loadUserDetails(callback: (Boolean, String?, String?, String?, String?, Boolean?) -> Unit) {
        val userId = userRepository.getUserId()

        if (userId == null) {
            callback(false, null, null, null, null, null)
            return
        }

        fireStore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val email = document.getString("email")
                    val displayName = document.getString("displayName")
                    val birthday = document.getString("birthday")
                    val profilePictureUrl = document.getString("profilePictureUrl")
                    val isGoogleAccount = document.getBoolean("isGoogleAccount")
                    callback(true, email, displayName, birthday, profilePictureUrl, isGoogleAccount)
                }
            }
    }
}

private fun determineDISCCombination(result: Map<String, Int>): String {
    val sortedTraits = result.entries.sortedByDescending { it.value }

    val primaryTrait = sortedTraits.getOrNull(0)?.key?.first()?.toString() ?: return "N/A"
    val secondaryTrait = sortedTraits.getOrNull(1)?.key?.first()?.toString()
    val tertiaryTrait = sortedTraits.getOrNull(2)?.key?.first()?.toString()
    val primaryCount = sortedTraits.getOrNull(0)?.value ?: 0
    val secondaryCount = sortedTraits.getOrNull(1)?.value ?: 0
    val tertiaryCount = sortedTraits.getOrNull(2)?.value ?: 0
    val quaternaryCount = sortedTraits.getOrNull(3)?.value ?: 0

    val threshold = 2

    val isSecondarySignificant = secondaryCount >= (primaryCount - threshold)
    val isTertiarySignificant = tertiaryCount >= (primaryCount - threshold * 3 / 2)
    val isQuaternarySignificant = quaternaryCount >= (primaryCount - threshold * 2)

    return when {
        isQuaternarySignificant -> "DISC"
        isTertiarySignificant -> {
            val threeTraitCombo = setOf(primaryTrait, secondaryTrait, tertiaryTrait)
            when (threeTraitCombo) {
                setOf("D", "I", "S") -> "DIS"
                setOf("D", "I", "C") -> "DIC"
                setOf("I", "S", "C") -> "ISC"
                setOf("D", "S", "C") -> "DSC"
                else -> primaryTrait
            }
        }

        isSecondarySignificant -> {
            val twoTraitCombo = setOf(primaryTrait, secondaryTrait)
            when (twoTraitCombo) {
                setOf("D", "I") -> "DI"
                setOf("D", "S") -> "DS"
                setOf("D", "C") -> "DC"
                setOf("I", "S") -> "IS"
                setOf("I", "C") -> "IC"
                setOf("S", "C") -> "SC"
                else -> primaryTrait
            }
        }

        else -> primaryTrait
    }
}