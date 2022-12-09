package com.msk.simpletodo.presentation.viewModel.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.domain.usecase.GetCategoryWithTodoUseCase
import com.msk.simpletodo.domain.usecase.TodoCreateUseCase
import com.msk.simpletodo.presentation.view.base.TodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val getCategoryWithTodoUseCase: GetCategoryWithTodoUseCase
) :
    ViewModel() {

    init {
        getCategoryWithTodo()
    }

    private val _categoryWithTodoResult: MutableStateFlow<TodoState<List<TodoCategoryWithTodo>>> =
        MutableStateFlow(TodoState.Loading)
    val categoryWithTodoResult = _categoryWithTodoResult.asStateFlow()

    private fun getCategoryWithTodo() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            getCategoryWithTodoUseCase.execute()
        }.onSuccess { data ->
            _categoryWithTodoResult.collect {
                _categoryWithTodoResult.emit(it)
            }
        }
    }
}