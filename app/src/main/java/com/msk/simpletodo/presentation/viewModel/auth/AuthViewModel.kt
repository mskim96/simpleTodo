package com.msk.simpletodo.presentation.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.msk.simpletodo.data.datastore.PreferDatastore
import com.msk.simpletodo.domain.model.UiEvent
import com.msk.simpletodo.domain.usecase.auth.SignInUseCase
import com.msk.simpletodo.domain.usecase.auth.SignOutUseCase
import com.msk.simpletodo.domain.usecase.auth.SignUpUseCase
import com.msk.simpletodo.presentation.util.toSuspendable
import com.msk.simpletodo.presentation.view.auth.oauth.actionCodeSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val preferDatastore: PreferDatastore,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    private val _signResult = MutableSharedFlow<UiEvent<String?>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val signResult = _signResult.asSharedFlow()

    fun createAccount(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            signUpUseCase(email, password).toSuspendable()
        }.onSuccess { result ->
            sendVerifyEmail(result.user)
            _signResult.emit(UiEvent.Success(email)) // for email send fragment
        }.onFailure { throwable ->
            _signResult.emit(UiEvent.Failed(throwable.message.toString()))
        }
    }

    fun signInAccount(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            signInUseCase(email, password).toSuspendable()
        }.onSuccess { result ->
            _signResult.emit(UiEvent.Success(result.user?.email))
        }.onFailure { throwable ->
            _signResult.emit(UiEvent.Failed(throwable.message))
        }
    }

    fun signOutAccount() = viewModelScope.launch(Dispatchers.IO) {
        signOutUseCase.invoke()
    }

    private suspend fun sendVerifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification(actionCodeSetting)?.toSuspendable()
    }
}