package com.msk.simpletodo.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.domain.model.SignUpUser
import com.msk.simpletodo.domain.model.UserState
import com.msk.simpletodo.domain.usecase.SignInUseCase
import com.msk.simpletodo.domain.usecase.SignUpUseCase
import com.msk.simpletodo.presentation.util.encryptECB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    /**
     * Livedata for signup: user information
     */
    private val _userDataEmail = MutableLiveData<String>()
    val userDataEmail: LiveData<String> get() = _userDataEmail

    private val _userDataPassword = MutableLiveData<String>()
    val userDataPassword: LiveData<String> get() = _userDataPassword


    // save result of sign up, sign in Long Data
    private val _userResult = MutableLiveData<UserState<Any>>()
    val userResult: LiveData<UserState<Any>> get() = _userResult

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
            _userResult.postValue(UserState.Success(data = result))
            return@launch
        }

    fun loginAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // get email information from database
            val result = signInUseCase.execute(email)

            if (result == null) {
                _userResult.postValue(UserState.Error(message = "Email does not exists", null))
                return@launch
            } else {
                // user information from result, and match input password and user password
                if (result.password != password.encryptECB()) {
                    _userResult.postValue(
                        UserState.Error(
                            message = "Password does not matched.",
                            null
                        )
                    )
                } else {
                    _userResult.postValue(UserState.Success(result))
                }
            }
        }
    }
}