package com.msk.simpletodo.presentation.viewModel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.msk.simpletodo.data.datastore.PreferDatastore
import com.msk.simpletodo.domain.usecase.auth.SignInUseCase
import com.msk.simpletodo.domain.usecase.auth.SignUpUseCase
import com.msk.simpletodo.presentation.util.SignUpUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val preferDatastore: PreferDatastore
) : ViewModel() {

    // Livedata for signup: user information
    private val _userDataEmail = MutableLiveData<String>()
    val userDataEmail: LiveData<String> get() = _userDataEmail

    private val _userDataPassword = MutableLiveData<String>()
    val userDataPassword: LiveData<String> get() = _userDataPassword

    private val _loginResult = MutableSharedFlow<String?>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val loginResult =
        _loginResult.asSharedFlow()

    // get username from datastore
    val userNameFlow: Flow<String?> = preferDatastore.getUsernameOnDataStore()

    // put user email from SignUpEmailFragment to live data
    fun putUserEmail(type: SignUpUser, email: String) {
        // put data on liveData
        if (type == SignUpUser.Email) {
            _userDataEmail.value = email
        }
    }

    fun putUserPassword(type: SignUpUser, password: String) {
        if (type == SignUpUser.Password) {
            _userDataPassword.value = password
        }
    }

    // create Account Method
    fun createAccount(email: String, password: String, username: String) =
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(email, password, username)
        }

    fun signInAccount(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            signInUseCase.execute(email, password).toSuspendable()
        }.onSuccess {
            _loginResult.emit(it.user?.displayName)
        }.onFailure {
            _loginResult.emit(null)
        }
    }
}

suspend fun <T> Task<T>.toSuspendable(): T {
    return suspendCoroutine { cont ->
        this.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                cont.resume(task.result)
            } else if (task.isCanceled) {
                cont.resumeWithException(CancellationException())
            } else {
                cont.resumeWithException(task.exception ?: Exception("Unknown"))
            }
        }
    }
}
