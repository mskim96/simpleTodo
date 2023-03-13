package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TodoDeleteUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(todo: TaskEntity): Int {
        return repository.deleteTodo(todo)
    }
}