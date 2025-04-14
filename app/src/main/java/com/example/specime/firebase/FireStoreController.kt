package com.example.specime.firebase

import android.net.Uri
import com.example.specime.screens.disc.test.Question
import com.example.specime.screens.disc.testdetail.AnsweredQuestion
import com.example.specime.screens.group.groupresult.MemberTestResult
import com.example.specime.screens.home.GroupInformation
import com.example.specime.screens.home.TestResult
import com.example.specime.userrepository.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FireStoreController @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val userRepository: UserRepository,
) {
    fun createGroup(
        emails: List<String>,
        groupName: String,
        callback: (Boolean) -> Unit
    ) {
        val currentUserEmail = userRepository.getUserEmail()!!
        val updatedEmails = emails.toMutableList()
        val userIds = mutableListOf<String>()

        updatedEmails.add(currentUserEmail)

        fun getUserIdByEmail(email: String, onComplete: (String?) -> Unit) {
            fireStore.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val userId = querySnapshot.documents.firstOrNull()?.id
                        onComplete(userId)
                    } else {
                        onComplete(null)
                    }
                }
        }

        val emailsToProcess = updatedEmails.size
        var processedEmails = 0

        for (email in updatedEmails) {
            getUserIdByEmail(email) { userId ->
                if (userId != null) {
                    userIds.add(userId)
                }
                processedEmails++
                if (processedEmails == emailsToProcess) {
                    val groupData = hashMapOf(
                        "groupName" to groupName,
                        "emails" to updatedEmails,
                        "userIds" to userIds,
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                    fireStore.collection("groups")
                        .add(groupData)
                        .addOnSuccessListener { documentRef ->
                            val groupId = documentRef.id

                            userRepository.updateGroup(groupId) { success ->
                                callback(success)
                            }
                        }
                }
            }
        }
    }

    fun fetchGroupInformations(callback: (List<GroupInformation>) -> Unit) {
        val userId = userRepository.getUserId()!!

        fireStore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                val groupIds = (document.get("groups") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                if (groupIds.isEmpty()) {
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                val batches = groupIds.chunked(10)
                val groupDocuments = mutableListOf<DocumentSnapshot>()
                var fetchedBatches = 0

                for (batch in batches) {
                    fireStore.collection("groups")
                        .whereIn(FieldPath.documentId(), batch)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            groupDocuments.addAll(querySnapshot.documents)
                            fetchedBatches++
                            if (fetchedBatches == batches.size) {
                                processGroupDocuments(groupDocuments, callback)
                            }
                        }
                        .addOnFailureListener {
                            fetchedBatches++
                            if (fetchedBatches == batches.size) {
                                processGroupDocuments(groupDocuments, callback)
                            }
                        }
                }
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    private fun processGroupDocuments(
        documents: List<DocumentSnapshot>,
        callback: (List<GroupInformation>) -> Unit
    ) {
        val groupResultsList = mutableListOf<GroupInformation>()
        var processedGroups = 0

        if (documents.isEmpty()) {
            callback(emptyList())
            return
        }

        for (document in documents) {
            val groupName = document.getString("groupName")
            val timestamp = document.getTimestamp("timestamp")
            val groupSize = (document.get("emails") as? List<*>)?.filterIsInstance<String>()?.size ?: 0
            val groupUserIds = (document.get("userIds") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

            fun finalizeGroup(testsDone: Int) {
                groupResultsList.add(
                    GroupInformation(
                        groupName = groupName,
                        timestamp = timestamp,
                        testsDone = testsDone,
                        groupSize = groupSize,
                        groupId = document.id
                    )
                )
                processedGroups++
                if (processedGroups == documents.size) {
                    callback(groupResultsList)
                }
            }

            if (groupUserIds.isEmpty()) {
                finalizeGroup(0)
            } else {
                val chunks = groupUserIds.chunked(10)
                val testUserIdsSet = mutableSetOf<String>()
                var completedChunks = 0

                for (chunk in chunks) {
                    fireStore.collection("test_results")
                        .whereIn("userId", chunk)
                        .whereEqualTo("groupId", document.id)
                        .get()
                        .addOnSuccessListener { testSnapshot ->
                            testSnapshot.documents.mapNotNull { it.getString("userId") }
                                .forEach { testUserIdsSet.add(it) }
                            completedChunks++
                            if (completedChunks == chunks.size) {
                                finalizeGroup(testUserIdsSet.size)
                            }
                        }
                        .addOnFailureListener {
                            completedChunks++
                            if (completedChunks == chunks.size) {
                                finalizeGroup(testUserIdsSet.size)
                            }
                        }
                }
            }
        }
    }

    fun listenForGroupTestResults(callback: (List<GroupInformation>) -> Unit): ListenerRegistration? {
        val userId = userRepository.getUserId() ?: return null

        return fireStore.collection("users").document(userId)
            .addSnapshotListener userListener@{ userSnapshot, userError ->
                if (userError != null || userSnapshot == null || !userSnapshot.exists()) {
                    callback(emptyList())
                    return@userListener
                }

                val groupIds = (userSnapshot.get("groups") as? List<*>)?.filterIsInstance<String>() ?: emptyList()
                if (groupIds.isEmpty()) {
                    callback(emptyList())
                    return@userListener
                }

                val groupResultsMap = mutableMapOf<String, GroupInformation>()
                val groupBatches = groupIds.chunked(10)
                var groupBatchesProcessed = 0

                groupBatches.forEach { batch ->
                    fireStore.collection("groups")
                        .whereIn(FieldPath.documentId(), batch)
                        .addSnapshotListener groupListener@{ groupSnapshot, groupError ->
                            if (groupError != null || groupSnapshot == null) {
                                groupBatchesProcessed++
                                if (groupBatchesProcessed == groupBatches.size) {
                                    callback(groupResultsMap.values.toList().sortedBy { it.timestamp })
                                }
                                return@groupListener
                            }

                            for (doc in groupSnapshot.documents) {
                                val groupIdFromDoc = doc.id
                                val groupName = doc.getString("groupName")
                                val timestamp = doc.getTimestamp("timestamp")
                                val groupSize = (doc.get("emails") as? List<*>)?.filterIsInstance<String>()?.size ?: 0
                                val groupUserIds = (doc.get("userIds") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

                                if (groupUserIds.isEmpty()) {
                                    groupResultsMap[groupIdFromDoc] = GroupInformation(
                                        groupName = groupName,
                                        timestamp = timestamp,
                                        testsDone = 0,
                                        groupSize = groupSize,
                                        groupId = groupIdFromDoc
                                    )
                                    callback(groupResultsMap.values.toList().sortedBy { it.timestamp })
                                } else {
                                    val userBatches = groupUserIds.chunked(10)
                                    val testUserIdsSet = mutableSetOf<String>()
                                    var completedTestBatches = 0

                                    userBatches.forEach { userBatch ->
                                        fireStore.collection("test_results")
                                            .whereIn("userId", userBatch)
                                            .whereEqualTo("groupId", groupIdFromDoc)
                                            .addSnapshotListener testListener@{ testSnapshot, testError ->
                                                if (testError != null || testSnapshot == null) {
                                                    completedTestBatches++
                                                    if (completedTestBatches == userBatches.size) {
                                                        groupResultsMap[groupIdFromDoc] = GroupInformation(
                                                            groupName = groupName,
                                                            timestamp = timestamp,
                                                            testsDone = testUserIdsSet.size,
                                                            groupSize = groupSize,
                                                            groupId = groupIdFromDoc
                                                        )
                                                        callback(groupResultsMap.values.toList().sortedBy { it.timestamp })
                                                    }
                                                    return@testListener
                                                }

                                                val distinctUserIdsInBatch = testSnapshot.documents
                                                    .mapNotNull { it.getString("userId") }
                                                    .toSet()
                                                testUserIdsSet += distinctUserIdsInBatch

                                                completedTestBatches++
                                                if (completedTestBatches == userBatches.size) {
                                                    groupResultsMap[groupIdFromDoc] = GroupInformation(
                                                        groupName = groupName,
                                                        timestamp = timestamp,
                                                        testsDone = testUserIdsSet.size,
                                                        groupSize = groupSize,
                                                        groupId = groupIdFromDoc
                                                    )
                                                    callback(groupResultsMap.values.toList().sortedBy { it.timestamp })
                                                }
                                            }
                                    }
                                }
                            }

                            groupBatchesProcessed++
                            if (groupBatchesProcessed == groupBatches.size) {
                                callback(groupResultsMap.values.toList().sortedBy { it.timestamp })
                            }
                        }
                }
            }
    }

    fun fetchQuestions(callback: (questions: List<Question>?, error: String?) -> Unit) {
        fireStore.collection("questions")
            .get()
            .addOnSuccessListener { result ->
                val questions = result.documents.mapNotNull { document ->
                    val questionText = document.getString("question") ?: return@mapNotNull null
                    val label = document.getString("label") ?: return@mapNotNull null
                    Question(
                        question = questionText,
                        label = label
                    )
                }
                callback(questions, null)
            }
            .addOnFailureListener { e ->
                callback(null, e.localizedMessage)
            }
    }

    fun fetchTestResults(callback: (results: List<TestResult>?) -> Unit) {
        val userId = userRepository.getUserId()!!

        fireStore.collection("test_results")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val results = querySnapshot.documents.mapNotNull { document ->
                    val resultData = document.get("result") as? Map<*, *>
                    val parsedResult = resultData?.mapNotNull { (key, value) ->
                        val keyStr = key as? String
                        val valueInt = (value as? Long)?.toInt()
                        if (keyStr != null && valueInt != null) keyStr to valueInt else null
                    }?.toMap() ?: emptyMap()

                    val trait = document.getString("trait")
                    val timestamp = document.getTimestamp("timestamp")
                    val detailId = document.getString("resultDetailId")
                    val groupName = document.getString("groupName")

                    if (resultData != null) {
                        TestResult(parsedResult, trait, timestamp, detailId, groupName)
                    } else null
                }
                callback(results)
            }
    }

    fun fetchResultDetail(
        resultDetailId: String,
        callback: (success: Boolean, details: List<AnsweredQuestion>) -> Unit
    ) {
        fireStore.collection("test_results_detail")
            .document(resultDetailId)
            .get()
            .addOnSuccessListener { document ->
                val answeredQuestions = mutableListOf<AnsweredQuestion>()

                if (document.exists()) {
                    val details = document.get("details") as? List<*>
                    details?.forEach { item ->
                        val map = item as? Map<*, *>
                        val question = map?.get("question") as? String ?: return@forEach
                        val answer = map["answer"] as? Boolean ?: return@forEach

                        answeredQuestions.add(
                            AnsweredQuestion(
                                question = question,
                                answer = answer
                            )
                        )
                    }
                    callback(true, answeredQuestions)
                }
            }
    }

    fun fetchGroupInformations(
        groupId: String,
        callback: (groupName: String?, currentUserLatestTestResult: TestResult?, otherUserResults: List<MemberTestResult>) -> Unit
    ) {
        val currentUserId = userRepository.getUserId()!!

        fireStore.collection("groups").document(groupId)
            .get()
            .addOnSuccessListener { groupDoc ->
                val groupName = groupDoc.getString("groupName")

                fireStore.collection("test_results")
                    .whereEqualTo("groupId", groupId)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener { testSnapshot ->
                        val latestResultsByUser = mutableMapOf<String, DocumentSnapshot>()

                        for (doc in testSnapshot.documents) {
                            val userId = doc.getString("userId") ?: continue
                            if (!latestResultsByUser.containsKey(userId)) {
                                latestResultsByUser[userId] = doc
                            }
                        }

                        var currentUserLatestTestResult: TestResult? = null
                        val otherUserResults = mutableListOf<MemberTestResult>()

                        for ((userId, doc) in latestResultsByUser) {
                            val resultData = doc.get("result") as? Map<*, *>
                            val parsedResult = resultData?.mapNotNull { (key, value) ->
                                val keyStr = key as? String
                                val valueInt = (value as? Long)?.toInt()
                                if (keyStr != null && valueInt != null) keyStr to valueInt else null
                            }?.toMap() ?: emptyMap()

                            val trait = doc.getString("trait")

                            if (userId == currentUserId) {
                                currentUserLatestTestResult = TestResult(
                                    result = parsedResult,
                                    trait = trait,
                                    timestamp = null,
                                    detailId = null,
                                    groupName = null
                                )
                            } else {
                                val username = doc.getString("username") ?: "Unknown"
                                val email = doc.getString("email") ?: "Unknown"
                                otherUserResults.add(
                                    MemberTestResult(
                                        trait = trait,
                                        username = username,
                                        email = email
                                    )
                                )
                            }
                        }
                        callback(groupName, currentUserLatestTestResult, otherUserResults)
                    }
            }
    }

    private fun saveProfileImageUrl(
        downloadUrl: String,
        callback: (Boolean) -> Unit
    ) {
        val userId = userRepository.getUserId()!!
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

    fun uploadTestResult(
        userId: String,
        groupName: String?,
        groupId: String?,
        result: Map<String, Int>,
        trait: String,
        detailData: List<Map<String, Any>>,
        callback: () -> Unit
    ) {
        fireStore.collection("test_results_detail")
            .add(mapOf("details" to detailData))
            .addOnSuccessListener { detailDoc ->
                val resultDetailId = detailDoc.id
                val data: MutableMap<String, Any> = mutableMapOf(
                    "userId" to userId,
                    "result" to result,
                    "trait" to trait,
                    "resultDetailId" to resultDetailId,
                    "timestamp" to FieldValue.serverTimestamp(),
                    "username" to userRepository.getUserDisplayName()!!,
                    "email" to userRepository.getUserEmail()!!
                )

                groupName?.let { data["groupName"] = it }
                groupId?.let { data["groupId"] = it }

                fireStore.collection("test_results")
                    .add(data)
                    .addOnSuccessListener { callback() }
            }
    }

    fun uploadProfilePicture(
        uri: Uri,
        callback: (Boolean, String?) -> Unit
    ) {
        val userId = userRepository.getUserId()!!
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
        val userId = userRepository.getUserId()!!
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
        val userId = userRepository.getUserId()!!

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