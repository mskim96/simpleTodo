package com.msk.simpletodo.presentation.viewModel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.datastore.PreferDatastore
import com.msk.simpletodo.domain.usecase.SignUpUseCase
import com.msk.simpletodo.presentation.util.SignUpUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val preferDatastore: PreferDatastore
) : ViewModel() {

    // Livedata for signup: user information
    private val _userDataEmail = MutableLiveData<String>()
    val userDataEmail: LiveData<String> get() = _userDataEmail

    // get username from datastore
    val userNameFlow: Flow<String?> = preferDatastore.getUsernameOnDataStore()

    // put user email from SignUpEmailFragment to live data
    fun putUserEmail(type: SignUpUser, email: String) {
        // put data on liveData
        if (type == SignUpUser.Email) {
            _userDataEmail.value = email
        }
    }

    // create Account Method
    fun createAccount(email: String, username: String) =
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(email = email, username = username)

            // save username on datastore
            preferDatastore.putUsernameOnDataStore(username)
            return@launch
        }
}