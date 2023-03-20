package com.sinxn.myhabits.domain.use_case.task

import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.domain.repository.TaskRepository
import com.sinxn.myhabits.util.settings.Order
import com.sinxn.myhabits.util.settings.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val tasksRepository: TaskRepository
) {
    operator fun invoke(date: Long, order: Order): Flow<List<TaskWithProgress>> {
        return tasksRepository.getAllTasks(date).map { tasks ->
            when (order.orderType) {
                is OrderType.ASC -> {
                    when (order) {
                        is Order.Alphabetical -> tasks.sortedBy { it.task.title }
                        is Order.DateCreated -> tasks.sortedBy { it.task.createdDate }
                        is Order.DateModified -> tasks.sortedBy { it.task.updatedDate }
                        is Order.Interval -> tasks.sortedBy { it.task.interval }
                    }
                }
                is OrderType.DESC -> {
                    when (order) {
                        is Order.Alphabetical -> tasks.sortedByDescending { it.task.title }
                        is Order.DateCreated -> tasks.sortedByDescending { it.task.createdDate }
                        is Order.DateModified -> tasks.sortedByDescending { it.task.updatedDate }
                        is Order.Interval -> tasks.sortedByDescending { it.task.interval }
                    }
                }
            }
        }
    }
}