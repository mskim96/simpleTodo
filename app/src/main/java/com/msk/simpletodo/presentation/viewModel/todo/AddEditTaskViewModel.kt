package com.msk.simpletodo.presentation.viewModel.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.R
import com.msk.simpletodo.data.Result
import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class AddEditTaskUiState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val isTaskComplete: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskSaved: Boolean = false
)

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private var taskId: Long = 0

    // MutableStateFlow for Add Task or Edit Task.
    private val _uiState = MutableStateFlow(AddEditTaskUiState())
    val uiState: StateFlow<AddEditTaskUiState> = _uiState.asStateFlow()

    init {
        if (taskId != 0L) {
            loadTask(taskId)
        }
    }

    fun saveTask() {
        with(uiState.value) {
            if (title.isEmpty() || description.isEmpty() || category.isEmpty()) {
                _uiState.update {
                    it.copy(userMessage = R.string.empty_task_message)
                }
                return
            }
        }
        if (taskId != 0L) {
            createNewTask()
        } else {
            updateTask()
        }
    }

    fun snackBarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    fun updateCategory(newCategory: String) {
        _uiState.update {
            it.copy(category = newCategory)
        }
    }

    fun updateNewDateTime(newDate: LocalDateTime) {
        _uiState.update {
            it.copy(dateTime = newDate)
        }
    }

    private fun createNewTask() = viewModelScope.launch {
        with(uiState.value) {
            val newTask = TaskEntity(
                title = title,
                description = description,
                category = category,
                dateTime = dateTime,
                isComplete = isTaskComplete
            )
            taskRepository.insertTask(newTask)
            _uiState.update {
                it.copy(isTaskSaved = true)
            }
        }
    }

    private fun updateTask() {
        viewModelScope.launch {
            val updateTask = TaskEntity(
                title = uiState.value.title,
                description = uiState.value.description,
                category = uiState.value.category,
                dateTime = uiState.value.dateTime,
                isComplete = uiState.value.isTaskComplete,
            )
            taskRepository.insertTask(updateTask)
            _uiState.update {
                it.copy(isTaskSaved = true)
            }
        }
    }

    private fun loadTask(taskId: Long) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).let { result ->
                if (result is Result.Success) {
                    val task = result.data
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            category = task.category,
                            dateTime = task.dateTime,
                            isTaskComplete = task.isComplete
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}