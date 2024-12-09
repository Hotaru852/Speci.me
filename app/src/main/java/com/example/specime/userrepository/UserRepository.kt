package com.example.specime.userrepository

import android.content.SharedPreferences
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) {
    private val _userState = MutableStateFlow(UserState())

    init {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let { updateUserState(it) }
    }

    private fun updateUserState(user: FirebaseUser) {
        _userState.value = UserState(
            currentUser = user,
            email = user.email,
            displayName = user.displayName,
            profilePictureUrl = user.photoUrl?.toString()
        )
    }

    private fun clearUserState() {
        _userState.value = UserState()
    }

    fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    private fun saveUserToFirestore(userId: String, username: String, email: String) {
        val userMap = mapOf(
            "userId" to userId,
            "username" to username,
            "email" to email,
            "birthday" to "1/1/2000",
            "profilePictureUrl" to ""
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

    fun changePasswordWithVerification(
        currentPassword: String,
        newPassword: String,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        val currentUser = _userState.value.currentUser
        if (currentUser != null && currentUser.email != null) {
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)

            currentUser.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        currentUser.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    callback(true, "Mật khẩu đã được thay đổi thành công")
                                } else {
                                    callback(false, updateTask.exception?.localizedMessage ?: "Lỗi khi thay đổi mật khẩu")
                                }
                            }
                    } else {
                        callback(false, "Mật khẩu hiện tại không chính xác")
                    }
                }
        } else {
            callback(false, "Người dùng chưa đăng nhập")
        }
    }

    fun sendResetPasswordEmail(email: String, callback: (success: Boolean, message: String?) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Email đặt lại mật khẩu đã được gửi")
                } else {
                    callback(false, task.exception?.localizedMessage ?: "Lỗi khi gửi email đặt lại mật khẩu")
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
                        updateUserState(currentUser)
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
                                            username = username,
                                            email = email
                                        )
                                        callback(true, "Đăng ký thành công. Vui lòng xác minh email của bạn")
                                    } else {
                                        currentUser.delete()
                                            .addOnCompleteListener {
                                                callback(false, "Email không tồn tại")
                                            }
                                    }
                                }
                        } else {
                            callback(false, updateTask.exception?.localizedMessage ?: "Lỗi khi cập nhật tên hiển thị")
                        }
                    }
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthUserCollisionException -> "Tài khoản đã tồn tại"
                        else -> exception?.localizedMessage ?: "Lỗi không xác định"
                    }
                    callback(false, errorMessage)
                }
            }
    }

    fun updateUsername(newUsername: String, callback: (success: Boolean, message: String?) -> Unit) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStore.collection("users")
                            .document(currentUser.uid)
                            .update("username", newUsername)
                            .addOnSuccessListener {
                                updateUserState(currentUser)
                                callback(true, "Tên người dùng đã được thay đổi thành công")
                            }
                            .addOnFailureListener { exception ->
                                callback(false, exception.localizedMessage ?: "Lỗi khi cập nhật tên người dùng")
                            }
                    } else {
                        callback(false, task.exception?.localizedMessage ?: "Lỗi khi cập nhật tên người dùng")
                    }
                }
        } else {
            callback(false, "Người dùng chưa đăng nhập")
        }
    }

    fun updateEmail(newEmail: String, callback: (success: Boolean, message: String?) -> Unit) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            currentUser.verifyBeforeUpdateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStore.collection("users")
                            .document(currentUser.uid)
                            .update("email", newEmail)
                            .addOnSuccessListener {
                                callback(true, "Email đã được gửi để xác nhận. Vui lòng kiểm tra hộp thư của bạn.")
                            }
                            .addOnFailureListener { exception ->
                                callback(false, exception.localizedMessage ?: "Lỗi khi cập nhật email.")
                            }
                    } else {
                        callback(false, task.exception?.localizedMessage ?: "Lỗi khi cập nhật email.")
                    }
                }
        } else {
            callback(false, "Người dùng chưa đăng nhập.")
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        sharedPreferences.edit().remove("rememberLogin").apply()
        clearUserState()
    }
}
