package com.msk.simpletodo.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msk.simpletodo.SignUpUser

class AuthViewModel : ViewModel() {

    /**
     * Livedata for signup: user information
     */
    private val _userDataEmail = MutableLiveData<String>()
    val userDataEmail: LiveData<String> get() = _userDataEmail

    private val _userDataPassword = MutableLiveData<String>()
    val userDataPassword: LiveData<String> get() = _userDataPassword

    fun putUserInformation(type: SignUpUser, information: String) {
        when (type) {
            SignUpUser.Email -> _userDataEmail.value = information
            SignUpUser.Password -> _userDataPassword.value = information
            else -> null
        }
    }
}