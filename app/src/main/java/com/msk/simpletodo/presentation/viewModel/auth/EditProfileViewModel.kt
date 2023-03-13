package com.msk.simpletodo.presentation.viewModel.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.auth.UserLocal
import com.msk.simpletodo.domain.usecase.auth.LocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    fun updateProfileVoice() {

    }
}