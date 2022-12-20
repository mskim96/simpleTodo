package com.msk.simpletodo.presentation.viewModel.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.usecase.todo.*
import com.msk.simpletodo.presentation.view.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val todoDeleteUseCase: TodoDeleteUseCase,
    private val todoCheckUseCase: TodoCheckUseCase,
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

    private val _todoWithCategoryById: MutableStateFlow<CategoryWithTodo?> =
        MutableStateFlow(null)
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
        }.onFailure {
            return@onFailure
        }
    }

    // Get TodoList by category id
    fun getTodoByCategoryId(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getTodoWithCategoryByIdUseCase.execute(id)
        }.onSuccess { data ->
            data.collect { _todoWithCategoryById.emit(it) }
        }.onFailure {
            return@onFailure
        }
    }

    /**
     * CRUD Method
     */
    fun createTodo(content: String, categoryType: Long) = viewModelScope.launch(Dispatchers.IO) {
        // [createTodo] does not update mutableStateFlow because it is not a logic to update that screen
        todoCreateUseCase.execute(content, categoryType)
    }

    fun deleteTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        todoDeleteUseCase.execute(todo) // Delete todoElement logic
        _todoWithCategoryById.update { data -> // and update todoWithCategory mutable state for ui
            data?.let { categoryWithTodo ->
                categoryWithTodo.copy(todo = categoryWithTodo.todo - todo)
            }
        }
    }

    fun checkTodo(todo: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        todoCheckUseCase.execute(todo)
        _todoWithCategoryById.update { data -> // Check todoElement logic
            data?.let { categoryWithTodo -> // // and update todoWithCategory mutable state for ui
                categoryWithTodo.copy(todo = categoryWithTodo.todo.map { currentTodo ->
                    if (currentTodo.tid == todo.tid) todo else currentTodo
                })
            }
        }
    }
}