package com.msk.simpletodo.presentation.viewModel.auth

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.auth.UserLocal
import com.msk.simpletodo.domain.usecase.auth.LocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject


data class EditProfileUiState(
    val profileUri: String = "",
    val voiceIntroduceUri: String = "",
    val isLoading: Boolean = true,
    val userMessage: Int? = null,
    val profileSaved: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val localUserCase: LocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _recordTime = MutableStateFlow(0)
    val recordTime = _recordTime.asStateFlow()

    private val _recordState = MutableStateFlow(false)
    val recordState = _recordState.asStateFlow()

    fun setProfileUri(uri: Uri?) {
        _uiState.update { it.copy(profileUri = uri.toString()) }
    }

    fun getUserInfo(userId: String) = viewModelScope.launch {
        val user = localUserCase.getUserById(userId)
        _uiState.update {
            it.copy(
                profileUri = user!!.profileImage,
                voiceIntroduceUri = user.voiceVio
            )
        }
    }

    fun updateProfileImage(userId: String) = viewModelScope.launch {
        val user = localUserCase.getUserById(userId)
        localUserCase.updateUser(user?.copy(profileImage = uiState.value.profileUri)!!)
    }

    fun startRecord() = viewModelScope.launch {
        _recordState.update { true }
        while (_recordTime.value <= 30 && _recordState.value) {
            _recordTime.update { it + 1 }
            Log.d("TAG", "startRecord: ${_recordTime.value}")
            delay(1000L)
        }
    }

    fun resetRecord() = viewModelScope.launch {
        _recordState.update { false }
        _recordTime.value = 0
    }

    fun updateProfileVoice() {

    }
}