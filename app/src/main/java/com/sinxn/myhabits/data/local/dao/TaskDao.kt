package com.sinxn.myhabits.data.local.dao

import androidx.room.*
import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM tasks LEFT  JOIN progress on tasks.id = progress.habit_id AND progress.date = :date")
    fun getTasksWithProgress(date: Long): Flow<List<TaskWithProgress>>


//    @Query("SELECT * FROM tasks INNER JOIN progress ON tasks.id = progress.habit_id WHERE progress.date = :date")
//    fun getAllTasks(date: Long): Flow<List<Task>>

    @Query("SELECT * FROM progress WHERE id = :id and date = :date")
    suspend fun getTask(date:Long, id: Int): Progress

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :title || '%'")
    fun getTasksByTitle(title: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

//    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :id")
//    suspend fun updateCompleted(id: Int, completed: Boolean)

}