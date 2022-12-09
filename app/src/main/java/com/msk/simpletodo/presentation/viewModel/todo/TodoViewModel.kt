package com.msk.simpletodo.presentation.viewModel.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.domain.usecase.GetCategoryWithTodoUseCase
import com.msk.simpletodo.domain.usecase.TodoCreateUseCase
import com.msk.simpletodo.presentation.view.base.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoCreateUseCase: TodoCreateUseCase,
    private val getCategoryWithTodoUseCase: GetCategoryWithTodoUseCase,
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

    private fun getCategoryWithTodo() = viewModelScope.launch(Dispatchers.IO) {
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
            }
        }.onFailure {
            _categoryWithTodoResult.emit(Result.Error(it))
        }
    }
}