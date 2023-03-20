package com.sinxn.myhabits.domain.use_case.task

import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateProgressUseCase @Inject constructor(
    private val tasksRepository: TaskRepository,
) {
    suspend operator fun invoke(progress: Progress) {
        tasksRepository.updateTaskProgress(progress)
    }
}