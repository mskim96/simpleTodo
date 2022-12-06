package com.msk.simpletodo.presentation.viewModel.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.domain.auth.model.SignUpUser
import com.msk.simpletodo.domain.auth.model.UserState
import com.msk.simpletodo.domain.auth.usecase.SignInUseCase
import com.msk.simpletodo.domain.auth.usecase.SignUpUseCase
import com.msk.simpletodo.presentation.util.encryptECB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val preferencesDataStore: DataStore<Preferences>
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


    // for datastore
    val userNameFlow: Flow<String?> = preferencesDataStore.data.map {
        val USER_USERNAME = stringPreferencesKey("user_username")
        it[USER_USERNAME]
    }

    val userIdFlow: Flow<Long?> = preferencesDataStore.data.map {
        val USER_ID = longPreferencesKey("user_id")
        it[USER_ID]
    }

    fun putUserInformation(type: SignUpUser, information: String) {
        // put data on liveData
        if (type == SignUpUser.Email) {
            _userDataEmail.value = information
        } else {
            _userDataPassword.value = information
        }
    }

    // save data store method
    suspend fun putUserInformationOnDataStore(id: Long, username: String) {
        val USER_ID = longPreferencesKey("user_id")
        val USER_USERNAME = stringPreferencesKey("user_username")

        preferencesDataStore.edit { settings ->
            settings[USER_ID] = id
            settings[USER_USERNAME] = username
        }
    }

    fun createAccount(email: String, password: String, username: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val result = signUpUseCase.execute(email, password, username)

            _userResult.postValue(UserState.Success(data = result))
            // complete validate, save uid, username
            putUserInformationOnDataStore(result, username)
            return@launch
        }

    fun loginAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // get email information from database
            val result = signInUseCase.execute(email)

            if (result != null) {
                // user information from result, and match input password and user password
                if (result.password != password.encryptECB()) {
                    _userResult.postValue(
                        UserState.Error(message = "Password does not matched.", null)
                    )
                } else {
                    // complete login, save userid, username
                    _userResult.postValue(UserState.Success(result))
                    putUserInformationOnDataStore(result.uid, result.username)
                }

            } else {
                _userResult.postValue(UserState.Error(message = "Email does not exists", null))
                return@launch
            }
        }
    }
}