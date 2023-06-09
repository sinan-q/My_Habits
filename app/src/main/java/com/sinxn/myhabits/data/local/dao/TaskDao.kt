package com.sinxn.myhabits.data.local.dao

import androidx.room.*
import com.sinxn.myhabits.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query(
        "SELECT * FROM tasks  WHERE tasks.due_date = 0 OR tasks.due_date >= :date; "
    )
    fun getTasksAndProgress(date: Long): Flow<List<TaskAndProgress>>


    @Transaction
    @Query(
        "SELECT * FROM tasks LEFT OUTER JOIN progress ON tasks.id = progress.habit_id AND progress.date = :date WHERE tasks.due_date = 0 OR tasks.due_date >= :date; "
    )
    fun getTasksWithProgress(date: Long): Flow<List<TaskWithProgress>>


//    @Query("SELECT * FROM tasks INNER JOIN progress ON tasks.id = progress.habit_id WHERE progress.date = :date")
//    fun getAllTasks(date: Long): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTask(id: Long): TaskWithProgresses

    @Query("SELECT streak FROM progress WHERE habit_id = :id AND date = :date")
    suspend fun getStreak(id: Long, date: Long): Int?

    @Query("SELECT is_completed FROM progress WHERE habit_id = :id AND date = :date")
    suspend fun isComplete(id: Long, date: Long): Boolean?


    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :title || '%'")
    fun getTasksByTitle(title: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Upsert
    suspend fun updateProgress(progress: Progress): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<Task>)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :id;")
    suspend fun deleteTask(id: Long)

    @Query("UPDATE progress SET streak = :streak WHERE habit_id = :id AND date = :date;")
    suspend fun updateStreak(id: Long, date: Long, streak: Int)


//    @Query("UPDATE tasks SET is_completed = :completed WHERE id = :id")
//    suspend fun updateCompleted(id: Int, completed: Boolean)

}