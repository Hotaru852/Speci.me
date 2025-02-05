package com.example.specime.screens.auths.components

fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> "Vui lòng nhập Email"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email không hợp lệ"
        email.length > 320 -> "Email không được vượt quá 320 ký tự"
        else -> null
    }
}

fun validateUsername(username: String): String? {
    return when {
        username.isEmpty() -> "Vui lòng nhập tên người dùng"
        else -> null
    }
}

fun validatePhoneNumber(phoneNumber: String): String? {
    return when {
        phoneNumber.isEmpty() -> "Vui lòng nhập số điện thoại"
        !phoneNumber.matches(Regex("^[0-9]*$")) || phoneNumber.length != 10 -> "Số điện thoại không hợp lệ"
        else -> null
    }
}

fun validateSigninPassword(password: String): String? {
    return when {
        password.isEmpty() -> "Vui lòng nhập mật khẩu"
        else -> null
    }
}

fun validateSignupPassword(password: String): String? {
    return when {
        password.isEmpty() -> "Vui lòng nhập mật khẩu"
        password.length < 6 -> "Mật khẩu phải chứa ít nhất 6 ký tự"
        !password.any { it.isDigit() } -> "Mật khẩu phải chứa ít nhất một số"
        !password.any { it.isUpperCase() } -> "Mật khẩu phải chứa ít nhất một chữ cái viết hoa"
        !password.any { it.isLowerCase() } -> "Mật khẩu phải chứa ít nhất một chữ cái viết thường"
        else -> null
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    return when {
        confirmPassword.isEmpty() -> "Vui lòng nhập lại mật khẩu"
        confirmPassword != password -> "Mật khẩu không khớp"
        else -> null
    }
}