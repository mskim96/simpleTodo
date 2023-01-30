package com.msk.simpletodo.domain.usecase.todo

import com.msk.simpletodo.domain.repository.TodoRepository
import javax.inject.Inject

class TaskByDateUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(date: String) = todoRepository.getTaskByDate(date)
}