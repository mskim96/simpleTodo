package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoEditUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(todo: TodoEntity) = todoRepository.editTodo(todo)
}