package com.msk.simpletodo.domain.model

import java.util.regex.Pattern

sealed interface SignUpUser {
    object Email : SignUpUser
    object Password : SignUpUser
    object Username : SignUpUser
}

fun <T : SignUpUser> T.validate(data: String): Boolean {
    when (this) {
        SignUpUser.Email -> {
            val emailPattern = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
            return Pattern.matches(emailPattern, data)
        }
        SignUpUser.Password -> if (data.length < 5) return false
        SignUpUser.Username -> if (data.isNullOrBlank()) return false
    }
    return true
}