package com.msk.simpletodo.presentation.viewModel.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.usecase.GetCategoryWithTodoUseCase
import com.msk.simpletodo.domain.usecase.GetTodoWithCategoryByIdUseCase
import com.msk.simpletodo.domain.usecase.TodoCreateUseCase
import com.msk.simpletodo.domain.usecase.TodoDeleteUseCase
import com.msk.simpletodo.presentation.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    /**
     * StateFlow for Update Ui
     */
    private val _categoryWithTodo: MutableStateFlow<UiState<List<CategoryWithTodo>>> =
        MutableStateFlow(UiState.Loading)
    val categoryWithTodo = _categoryWithTodo.asStateFlow()

    private val _todoWithCategoryById: MutableStateFlow<UiState<CategoryWithTodo>> =
        MutableStateFlow(UiState.Loading)
    val todoWithCategoryById = _todoWithCategoryById.asStateFlow()


    /**
     * Get Data from local Database
     */
    // Get Category with TodoList
    fun getCategoryWithTodo() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getCategoryWithTodoUseCase.execute()
        }.onSuccess { data ->
            data.collect { _categoryWithTodo.emit(UiState.Success(it)) }
        }.onFailure { throwable ->
            _categoryWithTodo.emit(UiState.Fail(throwable))
        }
    }

    // Get TodoList by category id
    fun getTodoByCategoryId(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getTodoWithCategoryByIdUseCase.execute(id)
        }.onSuccess { data ->
            data.collect { _todoWithCategoryById.emit(UiState.Success(it)) }
        }.onFailure { throwable ->
            _todoWithCategoryById.emit(UiState.Fail(throwable))
        }
    }

    /**
     * CRUD Method
     */
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