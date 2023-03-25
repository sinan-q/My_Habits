package com.sinxn.myhabits.domain.repository

import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(date: Long): Flow<List<TaskWithProgress>>

    suspend fun getTaskById(date: Long, id: Long): Task

    fun searchTasks(date: Long,title: String): Flow<List<Task>>

    suspend fun insertTask(task: Task): Long

    suspend fun updateTaskProgress(progress: Progress)

    suspend fun updateTask(task: Task)

    suspend fun completeTask(date: Long,id: Int, completed: Boolean)

    suspend fun deleteTask(task: Task)

}