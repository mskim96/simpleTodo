package com.msk.simpletodo.presentation.view.auth.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.auth.User
import com.msk.simpletodo.domain.repository.AuthRepository
import com.msk.simpletodo.domain.usecase.auth.LocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class EditProfileUiState(
    val email: String = "",
    val username: String = "",
    val profileImage: String = "",
    val bio: String = "",
    val voiceBio: String = "",
    val isLoading: Boolean = true,
    val userMessage: String = "",
    val isRecorded: RecordState = RecordState.INIT,
    val isUserSaved: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val localUserCase: LocalUseCase,
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val userId = EditProfileFragmentArgs.fromSavedStateHandle(savedStateHandle).userId

    init {
        loadUser(userId)
    }

    fun saveUserProfile() = viewModelScope.launch {
        repository.updateUser(
            User(
                userId = userId,
                email = uiState.value.email,
                username = uiState.value.username,
                profileImage = uiState.value.profileImage,
                bio = uiState.value.bio,
                voiceBio = uiState.value.voiceBio
            )
        )
        _uiState.update { it.copy(isUserSaved = true, userMessage = "Complete edit profile.") }
    }

    fun updateProfileImage(uri: Uri?) {
        _uiState.update { it.copy(profileImage = uri.toString()) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun updateBio(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun deleteProfileImage() {
        _uiState.update { it.copy(profileImage = "") }
    }

    fun deleteRecordBio() {
        _uiState.update { it.copy(bio = "") }
    }

    fun startRecording() {
        _uiState.update { it.copy(isRecorded = RecordState.START) }
    }

    fun stopRecording() {
        _uiState.update { it.copy(isRecorded = RecordState.STOP) }
    }

    fun resetRecording() {
        _uiState.update { it.copy(isRecorded = RecordState.INIT) }
    }

    private fun loadUser(userId: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            repository.getUserById(userId).let { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            email = user.email,
                            username = user.username,
                            profileImage = user.profileImage,
                            bio = user.bio,
                            voiceBio = user.voiceBio,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

enum class RecordState {
    INIT, START, STOP;
}