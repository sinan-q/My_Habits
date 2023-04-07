package com.sinxn.myhabits.domain.repository

import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.domain.model.TaskWithProgresses
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(date: Long): Flow<List<TaskWithProgress>>

    suspend fun getTaskById(id: Long): TaskWithProgresses

    suspend fun getStreak(id: Long, date: Long): Int
    suspend fun updateStreak(id: Long, date: Long, streak: Int)


    fun searchTasks(date: Long, title: String): Flow<List<Task>>

    suspend fun insertTask(task: Task): Long

    suspend fun updateTask(task: Task)

    suspend fun completeTask(date: Long, id: Long, completed: Boolean)

    suspend fun deleteTask(id: Long)
    suspend fun updateTaskProgress(progress: Progress, today: Long)
    suspend fun isComplete(id: Long, date: Long): Boolean
}