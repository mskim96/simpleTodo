package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.usecase.todo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val todoDeleteUseCase: TodoDeleteUseCase,
    private val todoCheckUseCase: TodoCheckUseCase,
) :
    ViewModel() {

    /**
     * Get Data from local Database
     */
    // Get Category with TodoList
    fun getCategoryWithTodo() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "getCategoryWithTodo: ")
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
        category: Int
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            // [createTodo] does not update mutableStateFlow because it is not a logic to update that screen
            todoCreateUseCase.execute(content, description, date, time, category)
        }

    fun deleteTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "getCategoryWithTodo: ")
    }

    fun checkTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        todoCheckUseCase.execute(todo)
        Log.d("TAG", "getCategoryWithTodo: ")
    }
}