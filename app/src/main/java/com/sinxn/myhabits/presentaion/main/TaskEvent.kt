package com.sinxn.myhabits.presentaion.main

import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.util.settings.Order

sealed class TaskEvent {
    data class CompleteTask(val task: Task, val complete: Boolean) : TaskEvent()
    data class GetTask(val task: TaskWithProgress) : TaskEvent()
    data class AddTask(val task: Task) : TaskEvent()
    data class SearchTasks(val query: String) : TaskEvent()
    data class UpdateOrder(val order: Order) : TaskEvent()
    data class ShowCompletedTasks(val showCompleted: Boolean) : TaskEvent()
    data class UpdateTask(val task: Task, val dueDateUpdated: Boolean) : TaskEvent()
    data class UpdateProgress(val progress: Progress) : TaskEvent()

    data class DeleteTask(val task: Task) : TaskEvent()
    data class OnDateChange(val date: Long): TaskEvent()

    object ErrorDisplayed: TaskEvent()
}