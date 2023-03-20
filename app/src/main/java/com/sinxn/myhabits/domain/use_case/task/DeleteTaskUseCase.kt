package com.sinxn.myhabits.domain.use_case.task

import android.content.Context
import com.mhss.app.mybrain.domain.model.Task
import com.sinxn.myhabits.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val context: Context
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.deleteTask(task)
    }
}