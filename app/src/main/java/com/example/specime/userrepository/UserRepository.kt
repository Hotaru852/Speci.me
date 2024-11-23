package com.example.specime.userrepository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState

    init {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let { updateUserState(it) }
    }

    private fun updateUserState(user: FirebaseUser) {
        _userState.value = UserState(
            currentUser = user,
            email = user.email,
            displayName = user.displayName,
            photoUrl = user.photoUrl?.toString()
        )
    }

    private fun clearUserState() {
        _userState.value = UserState()
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
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
            .addOnCompleteListener { signUpTask ->
                if (signUpTask.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    user?.updateProfile(
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()
                    )?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            user.sendEmailVerification()
                                .addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        callback(true, "Đăng ký thành công. Vui lòng xác minh email của bạn")
                                    } else {
                                        user.delete()
                                            .addOnCompleteListener { deleteTask ->
                                                if (deleteTask.isSuccessful) {
                                                    callback(false, "Email không tồn tại")
                                                } else {
                                                    callback(false, deleteTask.exception?.localizedMessage ?: "Lỗi khi xóa tài khoản")
                                                }
                                            }
                                    }
                                }
                        } else {
                            callback(false, updateTask.exception?.localizedMessage ?: "Lỗi khi cập nhật tên hiển thị")
                        }
                    }
                } else {
                    val errorMessage = when (val exception = signUpTask.exception) {
                        is FirebaseAuthUserCollisionException -> "Tài khoản đã tồn tại"
                        else -> exception?.localizedMessage ?: "Lỗi không xác định"
                    }
                    callback(false, errorMessage)
                }
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

    fun updateUsername(newUsername: String, callback: (success: Boolean, message: String?) -> Unit) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()
            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateUserState(user)
                        callback(true, "Tên người dùng đã được thay đổi thành công")
                    } else {
                        callback(false, task.exception?.localizedMessage ?: "Lỗi khi thay đổi tên ngươi dùng")
                    }
                }
        } else {
            callback(false, "Người dùng chưa đăng nhập")
        }
    }

    fun updateEmail(newEmail: String, callback: (success: Boolean, message: String?) -> Unit) {
        val currentUser = _userState.value.currentUser
        if (currentUser != null) {
            currentUser.verifyBeforeUpdateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, "Email đã được gửi để xác nhận. Vui lòng kiểm tra hộp thư của bạn.")
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
        clearUserState()
    }
}