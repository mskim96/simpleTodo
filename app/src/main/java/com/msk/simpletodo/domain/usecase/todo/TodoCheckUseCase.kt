package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TodoCheckUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(todo: TaskEntity) = repository.checkTodo(todo)
}