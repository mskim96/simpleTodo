package com.msk.simpletodo.domain.usecase

import com.msk.simpletodo.data.model.todo.CategoryWithTodo
import com.msk.simpletodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryWithTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    fun execute(): Flow<List<CategoryWithTodo>> {
        return todoRepository.getCategoryWithTodo()
    }
}