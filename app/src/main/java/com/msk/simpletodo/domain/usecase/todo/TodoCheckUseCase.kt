package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoCheckUseCase @Inject constructor(private val repository: TodoRepository) {
    suspend fun execute(todo: TodoEntity) {
        return repository.checkTodo(todo)
    }
}