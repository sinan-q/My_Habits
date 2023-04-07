package com.sinxn.myhabits.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


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
    @ColumnInfo(name = "due_date")
    val dueDate: Long = 0L,
    @ColumnInfo(name = "sub_tasks")
    val subTasks: List<SubTask> = emptyList(),
    val interval: Int = 0,
    val remainder: Boolean = false,
    )


@Entity(
    primaryKeys = ["habit_id", "date"],
    tableName = "progress",
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["habit_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Progress(
    @ColumnInfo(name = "habit_id")
    val habitId: Long = 0,
    val date: Long = 0L,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    val subTasks: List<SubTask> = emptyList(),
    @ColumnInfo(defaultValue = "0")
    val streak: Int = 0,
    val note: String = ""
)

