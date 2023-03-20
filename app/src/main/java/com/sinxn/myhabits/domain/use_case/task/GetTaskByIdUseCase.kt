package com.sinxn.myhabits.domain.use_case.task

import com.sinxn.myhabits.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val tasksRepository: TaskRepository
) {
    suspend operator fun invoke(id: Int) = tasksRepository.getTaskById(id)
}