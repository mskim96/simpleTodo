package com.msk.simpletodo.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.domain.model.SignUpUser
import com.msk.simpletodo.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    /**
     * Livedata for signup: user information
     */
    private val _userDataEmail = MutableLiveData<String>()
    val userDataEmail: LiveData<String> get() = _userDataEmail

    private val _userDataPassword = MutableLiveData<String>()
    val userDataPassword: LiveData<String> get() = _userDataPassword


    // save result of sign up, sign in Long Data
    private val _userResult = MutableLiveData<Long>()
    val userResult: LiveData<Long> get() = _userResult

    fun putUserInformation(type: SignUpUser, information: String) {
        // put data on liveData
        if (type == SignUpUser.Email) {
            _userDataEmail.value = information
        } else {
            _userDataPassword.value = information
        }
    }

    fun createAccount(email: String, password: String, username: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUseCase.execute(email, password, username)
            _userResult.postValue(result)
            return@launch
        }
}