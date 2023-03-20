package com.sinxn.myhabits.domain.use_case.task

import com.mhss.app.mybrain.domain.model.Task
import com.sinxn.myhabits.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTasksUseCase @Inject constructor(
    private val tasksRepository: TaskRepository
) {
    operator fun invoke(query: String): Flow<List<Task>> {
        return tasksRepository.searchTasks(query)
    }
}