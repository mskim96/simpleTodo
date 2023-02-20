package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TaskRepository
import javax.inject.Inject

class TaskByDateUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(date: String) = taskRepository.getTaskByDate(date)
}