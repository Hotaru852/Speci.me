package com.example.specime.firestore

import android.net.Uri
import com.example.specime.screens.test.Answer
import com.example.specime.screens.test.Question
import com.example.specime.userrepository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class FireStoreController @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val userRepository: UserRepository,
) {
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
            .addOnFailureListener { exception ->
                callback(null, exception.localizedMessage)
            }
    }

    fun uploadTestResult(
        userId: String,
        data: Map<String, Any>,
        callback: (success: Boolean) -> Unit
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
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false)
            }
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

    private fun saveProfileImageUrl(downloadUrl: String, callback: (Boolean) -> Unit) {
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

        val storageReference = FirebaseStorage.getInstance().reference.child("profile_pictures/$userId.jpg")

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

    fun loadUserDetails(callback: (Boolean, String?, String?, String?, String?) -> Unit) {
        val userId = userRepository.getUserId()

        if (userId == null) {
            callback(false, null, null, null, null)
            return
        }

        fireStore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val email = document.getString("email")
                    val displayName = document.getString("username")
                    val birthday = document.getString("birthday")
                    val profilePictureUrl = document.getString("profilePictureUrl")
                    callback(true, email, displayName, birthday, profilePictureUrl)
                } else {
                    fireStore.collection("users").document(userId)
                        .set(mapOf("email" to "", "displayName" to "", "birthday" to "", "profilePictureUrl" to ""), SetOptions.merge())
                        .addOnSuccessListener {
                            callback(true, "", "", "", "")
                        }
                        .addOnFailureListener {
                            callback(false, null, null, null, null)
                        }
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                callback(false, null, null, null, null)
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
    val isTertiarySignificant = tertiaryCount >= (primaryCount - threshold * 1/2)
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
                else -> "$primaryTrait$secondaryTrait$tertiaryTrait"
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
                else -> "$primaryTrait$secondaryTrait"
            }
        }
        else -> primaryTrait
    }
}