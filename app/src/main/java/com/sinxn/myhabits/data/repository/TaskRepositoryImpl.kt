package com.sinxn.myhabits.data.repository

import com.sinxn.myhabits.data.local.dao.TaskDao
import com.sinxn.myhabits.domain.model.*
import com.sinxn.myhabits.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {

    override fun getAllTasks(date: Long): Flow<List<TaskAndProgress>> {
        return taskDao.getTasksAndProgress(date)

    }

    override fun getAllTasksOnDate(date: Long): Flow<List<TaskWithProgress>> {
        return taskDao.getTasksWithProgress(date).map { tasksWithProgress ->
            tasksWithProgress.map { progress ->
                progress.progress = progress.progress ?: Progress(
                    habitId = progress.id,
                    date = date,
                    subTasks = progress.subTasks
                )
                progress
            }
        }
    }

    override suspend fun getTaskById(id: Long): TaskWithProgresses {
        return withContext(ioDispatcher) {
            taskDao.getTask(id)
        }
    }

    override suspend fun isComplete(id: Long, date: Long): Boolean {
        taskDao.isComplete(id, date).let { return it ?: false }
    }

    override suspend fun getStreak(id: Long, date: Long): Int {
        taskDao.getStreak(id, date).let { return it ?: 0 }
    }

    override suspend fun updateStreak(id: Long, date: Long, streak: Int) {
        taskDao.updateStreak(id, date, streak)
    }


    override fun searchTasks(date: Long, title: String): Flow<List<Task>> {
        return taskDao.getTasksByTitle(title)
    }

    override suspend fun insertTask(task: Task): Long {
        return withContext(ioDispatcher) {
            taskDao.insertTask(task)
        }
    }

    override suspend fun updateTaskProgress(progress: Progress, today: Long) {
        withContext(ioDispatcher) {
            var streak = -1
            if (progress.isCompleted)
                streak = getStreak(progress.habitId, progress.date - 1)
            taskDao.updateProgress(progress.copy(streak = ++streak))
            for (date in (progress.date + 1L)..today) {
                val isComplete = isComplete(progress.habitId, date)
                if (isComplete) updateStreak(progress.habitId, date, ++streak) else break
            }
        }

    }

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
