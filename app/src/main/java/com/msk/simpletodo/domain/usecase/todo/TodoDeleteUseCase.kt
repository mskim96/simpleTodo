package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.todo.TodoEntity
import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoDeleteUseCase @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoEntity): Int {
        return repository.deleteTodo(todo)
    }
}