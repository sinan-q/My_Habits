package com.sinxn.myhabits.domain.use_case.task

import com.mhss.app.mybrain.domain.model.Task
import com.sinxn.myhabits.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val tasksRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task): Long {
        return tasksRepository.insertTask(task)
    }
}
