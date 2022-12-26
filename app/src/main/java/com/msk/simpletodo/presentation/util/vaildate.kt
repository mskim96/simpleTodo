package com.msk.simpletodo.presentation.util

import java.util.regex.Pattern

const val EMAIL_PATTERN = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"

sealed interface SignUpUser {
    object Email : SignUpUser
    object Password : SignUpUser
    object Username : SignUpUser
}

fun <T : SignUpUser> T.validate(data: String): Boolean {
    when (this) {
        SignUpUser.Email -> return Pattern.matches(EMAIL_PATTERN, data)
        SignUpUser.Password -> if (data.length < 6) return false
        SignUpUser.Username -> if (data.isNullOrBlank() || data.length > 12) return false
    }
    return true
}