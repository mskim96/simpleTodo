package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.data.model.todo.TodoCategoryWithTodo
import com.msk.simpletodo.domain.repository.TodoRepository
import com.msk.simpletodo.presentation.view.base.TodoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoryWithTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    fun execute(): Flow<TodoState<List<TodoCategoryWithTodo>>> {
        return todoRepository.getCategoryWithTodo()
    }
}