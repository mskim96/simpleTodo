package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import com.msk.simpletodo.domain.usecase.todo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val todoEditUseCase: TodoEditUseCase,
    private val todoDeleteUseCase: TodoDeleteUseCase,
    private val todoCheckUseCase: TodoCheckUseCase,
    private val taskByDateUseCase: TaskByDateUseCase,
    private val taskUseCase: TaskUseCase,
    private val taskRepository: TaskRepository
) :
    ViewModel() {


    private val _taskState: MutableStateFlow<List<TaskEntity>> =
        MutableStateFlow(listOf())
    val taskState = _taskState.asStateFlow()

    private val _taskRecentState: MutableStateFlow<List<TaskEntity>> =
        MutableStateFlow(listOf())
    val taskRecentState = _taskRecentState.asStateFlow()

    private val _taskDetail: MutableStateFlow<TaskEntity> =
        MutableStateFlow(
            TaskEntity(
                0,
                "",
                "",
                "",
                LocalDateTime.now(),
                false,
                0L,
                0L,
                false
            )
        )
    val taskDetail = _taskDetail.asStateFlow()

    private val _taskSearchState: MutableStateFlow<List<TaskEntity>> =
        MutableStateFlow(listOf())
    val taskSearchState = _taskSearchState.asStateFlow()

    private val _recordTimeLimit: MutableStateFlow<Int> = MutableStateFlow(0)
    val recordTimeLimit = _recordTimeLimit.asStateFlow()

    /**
     * Get Data from local Database
     */
    // Get Category with TodoList
    fun getTaskByDate(date: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            taskByDateUseCase(date)
        }.onSuccess { result ->
            result.collectLatest { data ->
                _taskState.emit(data)
            }
        }
    }

    fun getTaskByQuery(query: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            taskUseCase.getTaskByQuery(query)
        }.onSuccess { result ->
            result.collectLatest { data -> _taskSearchState.emit(data) }
        }
    }

    fun getTaskByRecent() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            taskUseCase.getTaskByRecent()
        }.onSuccess { result ->
            result.collectLatest { data -> _taskRecentState.emit(data) }
        }
    }

    // Get TodoList by category id
    fun getTodoByCategoryId(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "getCategoryWithTodo: ")
    }

    /**
     * CRUD Method
     */
    fun createTodo(
        content: String,
        description: String,
        date: String,
        time: String,
        category: Int,
        notification: Boolean
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            // [createTodo] does not update mutableStateFlow because it is not a logic to update that screen
            todoCreateUseCase.execute(content, description, date, time, category, notification)
        }

    fun deleteTodo(todo: TaskEntity) = viewModelScope.launch(Dispatchers.IO) {
        val delete = todoDeleteUseCase(todo)
        _taskState.update { it - todo }
    }

    fun editTodo(todo: TaskEntity) = viewModelScope.launch(Dispatchers.IO) {
        val edit = todoEditUseCase(todo)
        Log.d("TAG", "editTodo: $edit")
        _taskState.update { it }
    }

    fun checkTodo(todo: TaskEntity) = viewModelScope.launch(Dispatchers.IO) {
        todoCheckUseCase(todo)
        _taskState.update { it }
    }

    fun emitTask(todo: TaskEntity) = viewModelScope.launch(Dispatchers.IO) {
//        _taskDetail.emit(todo)
    }

    fun startTimer() = viewModelScope.launch {
        while (_recordTimeLimit.value != 30) {
            delay(1000L)
            _recordTimeLimit.value += 1
            Log.d("TAG", "startTimer: ${_recordTimeLimit.value}")
        }
    }

    fun initTimer() = viewModelScope.launch {
        _recordTimeLimit.value = 0
    }
}