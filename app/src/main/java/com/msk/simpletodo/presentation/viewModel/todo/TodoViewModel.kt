package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.usecase.GetCategoryWithTodoUseCase
import com.msk.simpletodo.domain.usecase.GetTodoWithCategoryByIdUseCase
import com.msk.simpletodo.domain.usecase.TodoCreateUseCase
import com.msk.simpletodo.domain.usecase.TodoDeleteUseCase
import com.msk.simpletodo.presentation.util.convertTimestampToDOW
import com.msk.simpletodo.presentation.view.base.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val todoDeleteUseCase: TodoDeleteUseCase,
    private val getCategoryWithTodoUseCase: GetCategoryWithTodoUseCase,
    private val getTodoWithCategoryByIdUseCase: GetTodoWithCategoryByIdUseCase,
) :
    ViewModel() {

    init {
        getCategoryWithTodo()
    }

    private val _categoryWithTodoResult: MutableStateFlow<Result<List<TodoCategoryWithTodo>>> =
        MutableStateFlow(Result.Loading)
    val categoryWithTodoResult = _categoryWithTodoResult.asStateFlow()

    private val _categoryWithTodoSize: MutableStateFlow<Int> =
        MutableStateFlow(0)
    val categoryWithTodoSize = _categoryWithTodoSize.asStateFlow()

    private val _categoryWithTodoToday: MutableStateFlow<Int> =
        MutableStateFlow(0)
    val categoryWithTodoToday = _categoryWithTodoToday.asStateFlow()

    private val _todoWithCategoryById: MutableStateFlow<Result<TodoCategoryWithTodo>> =
        MutableStateFlow(Result.Loading)
    val todoWithCategoryById = _todoWithCategoryById.asStateFlow()

    fun getCategoryWithTodo() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            // execute data
            getCategoryWithTodoUseCase.execute()
        }.onSuccess { data ->

            data.collect {
                // emit Success all list data
                _categoryWithTodoResult.emit(Result.Success(it))

                // for todoList size
                var result = 0
                Result.Success(it.forEach {
                    result += it.todo.size
                    _categoryWithTodoSize.emit(result)
                })

                var todayResult = mutableListOf<TodoEntity>()
                Result.Success(it.map {
                    it.todo.forEach {
                        if (convertTimestampToDOW(it.createdAt) == convertTimestampToDOW(null)) {
                            todayResult.add(it)
                        }
                    }
                    _categoryWithTodoToday.emit(todayResult.size)
                })
            }
        }.onFailure {
            _categoryWithTodoResult.emit(Result.Error(it))
        }
    }

    fun getTodoByCategoryId(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getTodoWithCategoryByIdUseCase.execute(id)
        }.onSuccess { data ->
            data.collect {
                _todoWithCategoryById.emit(Result.Success(it))
            }
        }
    }

    fun createTodo(content: String, categoryType: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            todoCreateUseCase.execute(content, categoryType)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDeleteUseCase.execute(todo)
        }
    }
}