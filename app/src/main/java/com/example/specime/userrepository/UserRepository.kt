package com.example.specime.userrepository

import android.content.SharedPreferences
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
) {
    fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun getUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun getUserDisplayName() : String? {
        return firebaseAuth.currentUser?.displayName
    }

    private fun saveUserToFirestore(
        userId: String,
        displayName: String,
        email: String,
        profilePictureUrl: String,
        isGoogleAccount: Boolean
    ) {
        val userMap = mapOf(
            "displayName" to displayName,
            "email" to email,
            "birthday" to "1/1/2000",
            "profilePictureUrl" to profilePictureUrl,
            "isGoogleAccount" to isGoogleAccount,
            "groups" to listOf<String>()
        )
        fireStore.collection("users")
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun startAuthStateListener() {
        FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null && user.isEmailVerified) {
                fireStore.collection("users")
                    .document(user.uid)
                    .update("email", user.email)
            }
        }
    }

    fun changePasswordWithVerification(
        currentPassword: String,
        newPassword: String,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null && currentUser.email != null) {
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)

            currentUser.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        currentUser.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    callback(true, null)
                                }
                            }
                    } else {
                        callback(false, "Sai mật khẩu")
                    }
                }
        }
    }

    fun sendResetPasswordEmail(
        email: String,
        callback: (success: Boolean) -> Unit
    ) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                }
            }
    }

    fun signInWithGoogle(
        idToken: String,
        callback: (success: Boolean) -> Unit
    ) {
        val authCredential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser

                    if (currentUser != null) {
                        val userDocRef = fireStore.collection("users").document(currentUser.uid)
                        userDocRef.get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    val displayName = currentUser.displayName ?: ""
                                    val email = currentUser.email ?: ""

                                    saveUserToFirestore(
                                        userId = currentUser.uid,
                                        displayName = displayName,
                                        email = email,
                                        profilePictureUrl = currentUser.photoUrl.toString(),
                                        isGoogleAccount = true
                                    )
                                }
                                currentUser.email?.let { email ->
                                    syncUserGroups(email, currentUser.uid)
                                }
                                callback(true)
                            }
                    }
                }
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    if (currentUser?.isEmailVerified == true) {
                        callback(true, null)
                    } else {
                        firebaseAuth.signOut()
                        callback(false, "Email chưa được xác minh")
                    }
                } else {
                    callback(false, "Sai tài khoản hoặc mật khẩu")
                }
            }
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        username: String,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()
                    )?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            currentUser.sendEmailVerification()
                                .addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        saveUserToFirestore(
                                            userId = currentUser.uid,
                                            displayName = username,
                                            email = email,
                                            profilePictureUrl = "",
                                            isGoogleAccount = false
                                        )
                                        syncUserGroups(email, currentUser.uid)
                                        callback(true, "")
                                    } else {
                                        currentUser.delete()
                                            .addOnCompleteListener {
                                                callback(false, "Email không tồn tại")
                                            }
                                    }
                                }
                        }
                    }
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) callback(
                        false,
                        "Email đã được sử dụng"
                    )
                }
            }
    }

    fun updateUserDisplayName(
        newDisplayName: String,
        callback: (success: Boolean) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newDisplayName)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStore.collection("users")
                            .document(currentUser.uid)
                            .update("displayName", newDisplayName)
                            .addOnSuccessListener {
                                callback(true)
                            }
                    }
                }
        }
    }

    fun updateEmail(
        newEmail: String,
        callback: (success: Boolean) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser
        currentUser?.verifyBeforeUpdateEmail(newEmail)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true)
            }
        }
    }

    fun updateGroup(
        groupId: String,
        callback: (success: Boolean) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val userDocRef = fireStore.collection("users").document(currentUser.uid)

            userDocRef.update("groups", FieldValue.arrayUnion(groupId))
                .addOnSuccessListener {
                    callback(true)
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        sharedPreferences.edit().remove("rememberLogin").apply()
    }

    private fun syncUserGroups(email: String, userId: String) {
        val userDocRef = fireStore.collection("users").document(userId)

        fireStore.collection("groups")
            .whereArrayContains("emails", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents.forEach { groupDoc ->
                        val groupId = groupDoc.id
                        userDocRef.update("groups", FieldValue.arrayUnion(groupId))
                        fireStore.collection("groups")
                            .document(groupId)
                            .update("userIds", FieldValue.arrayUnion(userId))
                    }
                }
            }
    }
}