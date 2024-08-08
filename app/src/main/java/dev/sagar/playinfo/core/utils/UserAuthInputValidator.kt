package dev.sagar.playinfo.core.utils

import android.util.Patterns
import javax.inject.Inject

private const val PASSWORD_MIN_LENGTH = 6

class UserAuthInputValidator @Inject constructor() {

    fun isCreateAccountInputValid(name: String, email: String, password: String): Boolean {
        return nameValid(name) == null &&
            emailValid(email) == null &&
            passwordValid(password) == null
    }

    fun isLoginInputValid(email: String, password: String): Boolean {
        return emailValid(email) == null &&
            passwordValid(password) == null
    }

    fun nameValid(name: String): String? {
        return if (name.isNotBlank()) {
            null
        } else {
            "Name is required"
        }
    }

    fun emailValid(email: String): String? {
        return if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            null
        } else {
            "Invalid email"
        }
    }

    fun passwordValid(password: String): String? {
        return if (password.isNotBlank() && password.length >= PASSWORD_MIN_LENGTH) {
            null
        } else {
            "Password must be at least $PASSWORD_MIN_LENGTH characters"
        }
    }
}
