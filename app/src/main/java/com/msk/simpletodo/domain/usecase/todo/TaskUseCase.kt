package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    fun getTaskByQuery(query: String) = taskRepository.getTaskByQuery(query)

    fun getTaskByRecent() = taskRepository.getTaskByRecent()
}