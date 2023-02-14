package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.usecase.todo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val todoEditUseCase: TodoEditUseCase,
    private val todoDeleteUseCase: TodoDeleteUseCase,
    private val todoCheckUseCase: TodoCheckUseCase,
    private val taskByDateUseCase: TaskByDateUseCase,
    private val taskUseCase: TaskUseCase,
) :
    ViewModel() {


    private val _taskState: MutableStateFlow<List<TodoEntity>> =
        MutableStateFlow(listOf())
    val taskState = _taskState.asStateFlow()

    private val _taskRecentState: MutableStateFlow<List<TodoEntity>> =
        MutableStateFlow(listOf())
    val taskRecentState = _taskRecentState.asStateFlow()

    private val _taskDetail: MutableStateFlow<TodoEntity> =
        MutableStateFlow(TodoEntity(0, "", "", 0, 0, "", "", ""))
    val taskDetail = _taskDetail.asStateFlow()

    private val _taskSearchState: MutableStateFlow<List<TodoEntity>> =
        MutableStateFlow(listOf())
    val taskSearchState = _taskSearchState.asStateFlow()

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

    fun deleteTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        val delete = todoDeleteUseCase(todo)
        _taskState.update { it - todo }
    }

    fun editTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        val edit = todoEditUseCase(todo)
        Log.d("TAG", "editTodo: $edit")
        _taskState.update { it }
    }

    fun checkTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        todoCheckUseCase(todo)
        _taskState.update { it }
    }

    fun emitTask(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        _taskDetail.emit(todo)
    }


}