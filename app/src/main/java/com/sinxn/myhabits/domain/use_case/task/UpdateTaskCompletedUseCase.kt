package com.sinxn.myhabits.domain.use_case.task

import com.sinxn.myhabits.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskCompletedUseCase @Inject constructor(
    private val tasksRepository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int, completed: Boolean) {
       // tasksRepository.completeTask(taskId, completed)
    }
}