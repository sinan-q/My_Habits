package com.sinxn.myhabits.data.repository

import android.util.Log
import com.sinxn.myhabits.data.local.dao.TaskDao
import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.domain.model.TaskWithProgresses
import com.sinxn.myhabits.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {

    override fun getAllTasks(date: Long): Flow<List<TaskWithProgress>> {
        return taskDao.getTasksWithProgress(date)
    }

    override suspend fun getTaskById(id: Long): TaskWithProgresses {
        return withContext(ioDispatcher) {
            taskDao.getTask(id)
        }
    }

    override fun searchTasks(date: Long, title: String): Flow<List<Task>> {
        return taskDao.getTasksByTitle(title)
    }

    override suspend fun insertTask(task: Task): Long {
        return withContext(ioDispatcher) {
            taskDao.insertTask(task)
        }
    }

    override suspend fun updateTaskProgress(progress: Progress) {
        withContext(ioDispatcher) {
            Log.d("TAG", "updateTaskProgress: ${progress.subTasks.size}")
            taskDao.updateProgress(progress)
        }    }

    override suspend fun updateTask(task: Task) {
        withContext(ioDispatcher) {
            taskDao.updateTask(task)
        }
    }

    override suspend fun completeTask(date: Long, id: Long, completed: Boolean) {
//        withContext(ioDispatcher) {
//            taskDao.updateCompleted(id, completed)
//        }
    }

    override suspend fun deleteTask(id: Long) {
        withContext(ioDispatcher) {
            taskDao.deleteTask(id)
        }
    }

}
