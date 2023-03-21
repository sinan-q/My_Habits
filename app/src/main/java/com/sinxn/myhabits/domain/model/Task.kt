package com.sinxn.myhabits.domain.model

import androidx.room.*


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val habitId: Long = 0,
    val title: String,
    val emoji: String,
    val category: Boolean = true,
    @ColumnInfo(name = "created_date")
    val createdDate: Long = 0L,
    @ColumnInfo(name = "updated_date")
    val updatedDate: Long = 0L,
    @ColumnInfo(name = "sub_tasks")
    val subTasks: List<SubTask> = emptyList(),
    val interval: Int = 0,
    val remainder: Boolean = false,


)

data class TaskWithProgress(
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val title: String,
    val emoji: String,
    val category: Boolean = true,
    @ColumnInfo(name = "created_date")
    val createdDate: Long = 0L,
    @ColumnInfo(name = "updated_date")
    val updatedDate: Long = 0L,
    @ColumnInfo(name = "sub_tasks")
    val subTasks: List<SubTask> = emptyList(),
    val interval: Int = 0,
    val remainder: Boolean = false,
    @Embedded var progress: Progress?
)

@Entity(
    tableName = "progress",
    foreignKeys = [ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns = ["habit_id"])]
)
data class Progress(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "progress_id")
    val id: Long = 0L,
    @ColumnInfo(name = "habit_id")
    val habitId: Long = 0,
    val date: Long = 0L,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    val subTasks: List<SubTask> = emptyList(),

    )

