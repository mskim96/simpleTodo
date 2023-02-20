package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.data.model.task.TaskEntity
import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TodoEditUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(todo: TaskEntity) = taskRepository.editTodo(todo)
}