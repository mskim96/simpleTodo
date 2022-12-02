package com.msk.simpletodo

import java.util.regex.Pattern

sealed interface SignUpUser {
    object Email : SignUpUser
    object Password : SignUpUser
    object Username : SignUpUser

    companion object {
        fun validate(type: SignUpUser, data: String): Boolean {
            when (type) {
                Email -> {
                    val emailPattern = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
                    return Pattern.matches(emailPattern, data)
                }

                Password -> {
                    if (data.length < 5) {
                        return false
                    }
                }

                Username -> {
                    if (data.isNullOrBlank()) {
                        return false
                    }
                }
            }
            return true
        }
    }
}