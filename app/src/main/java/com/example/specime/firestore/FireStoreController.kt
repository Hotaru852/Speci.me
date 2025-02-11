package com.example.specime.firestore

import android.net.Uri
import com.example.specime.screens.contacts.main.Friend
import com.example.specime.screens.contacts.subs.friendrequest.FriendRequest
import com.example.specime.screens.contacts.subs.friendrequest.PendingRequest
import com.example.specime.screens.disc.sub.Answer
import com.example.specime.screens.disc.sub.Question
import com.example.specime.userrepository.UserRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FireStoreController @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val userRepository: UserRepository,
) {
    private var friendRequestListener: ListenerRegistration? = null

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

    fun fetchFriends(callback: (success: Boolean, friends: List<Friend>) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, emptyList())

        fireStore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val friendIds =
                    (document.get("friends") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                if (friendIds.isEmpty()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val friendsCollection = fireStore.collection("users")
                val batches = friendIds.chunked(10)
                val friendsList = mutableListOf<Friend>()
                var completedBatches = 0

                batches.forEach { batch ->
                    friendsCollection.whereIn(FieldPath.documentId(), batch)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            querySnapshot.documents.forEach { doc ->
                                val friend = Friend(
                                    userId = doc.id,
                                    displayName = doc.getString("displayName") ?: "Unknown",
                                    profilePictureUrl = doc.getString("profilePictureUrl") ?: ""
                                )
                                friendsList.add(friend)
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
                                callback(true, friendsList) // Return whatever was fetched
                            }
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                callback(false, emptyList())
            }
    }

    fun fetchUserRelationships(
        callback: (success: Boolean, friends: List<String>?, pendings: List<String>?, requests: List<String>?) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return
        val userRef = fireStore.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            val friends =
                (document.get("friends") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
            val pendingsRef = userRef.collection("pendings")
            val requestsRef = userRef.collection("requests")

            pendingsRef.get().addOnSuccessListener { pendingSnapshot ->
                val pendings = pendingSnapshot.documents.map { it.id }

                requestsRef.get().addOnSuccessListener { requestsSnapshot ->
                    val requests = requestsSnapshot.documents.map { it.id }
                    callback(true, friends, pendings, requests)
                }
            }
        }
    }

    fun fetchFriendRequests(callback: (success: Boolean, requests: List<FriendRequest>) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, emptyList())

        fireStore.collection("users").document(userId).collection("requests")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val requestEntries = querySnapshot.documents.mapNotNull { doc ->
                    val senderId = doc.id
                    val timestamp = doc.getLong("timestamp") ?: return@mapNotNull null
                    senderId to timestamp
                }

                val senderIds = requestEntries.map { it.first }
                if (senderIds.isEmpty()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val batches = senderIds.chunked(10)
                val requestsList = mutableListOf<FriendRequest>()
                var completedBatches = 0

                batches.forEach { batch ->
                    fireStore.collection("users")
                        .whereIn(FieldPath.documentId(), batch)
                        .get()
                        .addOnSuccessListener { userQuerySnapshot ->
                            userQuerySnapshot.documents.forEach { doc ->
                                val senderId = doc.id
                                val displayName = doc.getString("displayName") ?: ""
                                val profilePictureUrl = doc.getString("profilePictureUrl") ?: ""
                                val timestamp =
                                    requestEntries.find { it.first == senderId }?.second ?: 0L

                                requestsList.add(
                                    FriendRequest(
                                        senderId = senderId,
                                        displayName = displayName,
                                        profilePictureUrl = profilePictureUrl,
                                        timestamp = timestamp
                                    )
                                )
                            }
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(true, requestsList.sortedBy { it.timestamp })
                            }
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(false, emptyList())
                            }
                        }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                callback(false, emptyList())
            }
    }

    fun fetchPendings(callback: (success: Boolean, pendings: List<PendingRequest>) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, emptyList())

        fireStore.collection("users").document(userId).collection("pendings")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val pendingEntries = querySnapshot.documents.mapNotNull { doc ->
                    val recipientId = doc.id
                    val timestamp = doc.getLong("timestamp") ?: return@mapNotNull null
                    recipientId to timestamp
                }

                val recipientIds = pendingEntries.map { it.first }
                if (recipientIds.isEmpty()) {
                    callback(true, emptyList())
                    return@addOnSuccessListener
                }

                val batches = recipientIds.chunked(10)
                val pendingList = mutableListOf<PendingRequest>()
                var completedBatches = 0

                batches.forEach { batch ->
                    fireStore.collection("users")
                        .whereIn(FieldPath.documentId(), batch)
                        .get()
                        .addOnSuccessListener { userQuerySnapshot ->
                            userQuerySnapshot.documents.forEach { doc ->
                                val recipientId = doc.id
                                val displayName = doc.getString("displayName") ?: "Unknown"
                                val profilePictureUrl = doc.getString("profilePictureUrl") ?: ""
                                val timestamp =
                                    pendingEntries.find { it.first == recipientId }?.second ?: 0L

                                pendingList.add(
                                    PendingRequest(
                                        recipientId = recipientId,
                                        displayName = displayName,
                                        profilePictureUrl = profilePictureUrl,
                                        timestamp = timestamp
                                    )
                                )
                            }
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(true, pendingList.sortedBy { it.timestamp })
                            }
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            completedBatches++
                            if (completedBatches == batches.size) {
                                callback(false, emptyList())
                            }
                        }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                callback(false, emptyList())
            }
    }

    fun fetchFriendRequestCount(callback: (success: Boolean, count: Int) -> Unit) {
        val userId = userRepository.getUserId() ?: return callback(false, 0)
        val requestsRef = fireStore.collection("users").document(userId).collection("requests")

        requestsRef.get()
            .addOnSuccessListener { querySnapshot ->
                callback(true, querySnapshot.size())
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false, 0)
            }
    }

    fun listenForFriendRequests(onUpdate: (count: Int) -> Unit) {
        val userId = userRepository.getUserId() ?: return
        val userRef = fireStore.collection("users")
            .document(userId)
            .collection("requests")

        friendRequestListener?.remove()

        friendRequestListener = userRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                onUpdate(snapshot.size())
            }
        }
    }

    fun listenForFriendUpdates(onFriendsUpdated: (List<Friend>) -> Unit) {
        val userId = userRepository.getUserId() ?: return

        fireStore.collection("users").document(userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val friendIds =
                        (snapshot.get("friends") as? List<*>)?.filterIsInstance<String>()
                            ?: emptyList()

                    if (friendIds.isEmpty()) {
                        onFriendsUpdated(emptyList())
                        return@addSnapshotListener
                    }

                    fireStore.collection("users")
                        .whereIn(FieldPath.documentId(), friendIds)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            val friends = querySnapshot.documents.mapNotNull { doc ->
                                Friend(
                                    userId = doc.id,
                                    displayName = doc.getString("displayName") ?: "",
                                    profilePictureUrl = doc.getString("profilePictureUrl") ?: ""
                                )
                            }
                            onFriendsUpdated(friends)
                        }
                }
            }
    }

    fun removeFriendRequestListener() {
        friendRequestListener?.remove()
        friendRequestListener = null
    }

    fun sendFriendRequest(
        recipientId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userPendingRef = fireStore.collection("users").document(userId).collection("pendings")
            .document(recipientId)
        val recipientRequestRef =
            fireStore.collection("users").document(recipientId).collection("requests")
                .document(userId)

        val data = mapOf("timestamp" to System.currentTimeMillis())

        fireStore.runBatch { batch ->
            batch.set(userPendingRef, data)
            batch.set(recipientRequestRef, data)
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { callback(false) }
    }

    fun acceptFriendRequest(
        senderId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val senderRef = fireStore.collection("users").document(senderId)
        val userRef = fireStore.collection("users").document(userId)
        val senderPendingRef = senderRef.collection("pendings").document(userId)
        val userRequestRef = userRef.collection("requests").document(senderId)

        fireStore.runBatch { batch ->
            batch.delete(senderPendingRef)
            batch.delete(userRequestRef)
            batch.update(userRef, "friends", FieldValue.arrayUnion(senderId))
            batch.update(senderRef, "friends", FieldValue.arrayUnion(userId))
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { callback(false) }
    }

    fun declineFriendRequest(
        senderId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val senderPendingRef =
            fireStore.collection("users").document(senderId).collection("pendings").document(userId)
        val userRequestRef =
            fireStore.collection("users").document(userId).collection("requests").document(senderId)

        fireStore.runBatch { batch ->
            batch.delete(senderPendingRef)
            batch.delete(userRequestRef)
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { callback(false) }
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

    fun retrieveFriendRequest(
        recipientId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId() ?: return callback(false)
        val userPendingRef = fireStore.collection("users").document(userId).collection("pendings")
            .document(recipientId)
        val recipientRequestRef =
            fireStore.collection("users").document(recipientId).collection("requests")
                .document(userId)

        fireStore.runBatch { batch ->
            batch.delete(userPendingRef)
            batch.delete(recipientRequestRef)
        }.addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { callback(false) }
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