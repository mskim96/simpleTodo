package com.msk.simpletodo.presentation.view.auth.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.auth.User
import com.msk.simpletodo.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileDetailUi(
    val user: User? = null,
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileDetailUi())
    val uiState = _uiState.asStateFlow()

    private val userId = ProfileDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).userId

    init {
        loadUser(userId)
    }

    fun navEditProfile() {
        _uiState.update { it.copy(isEdit = true) }
    }

    fun navEditProfileComplete() {
        _uiState.update { it.copy(isEdit = false) }
    }

    private fun loadUser(userId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getUserById(userId).let { user ->
                if (user != null) {
                    _uiState.update {
                        it.copy(
                            user = user, isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}