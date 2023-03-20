package com.mhss.app.mybrain.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    val title: String,
    val emoji: String,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    val category: Boolean = true,
    @ColumnInfo(name = "created_date")
    val createdDate: Long = 0L,
    @ColumnInfo(name = "updated_date")
    val updatedDate: Long = 0L,

    @ColumnInfo(name = "sub_tasks")
    val interval: Int = 0,
    val remainder: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subTasks: List<SubTask> = emptyList()
)
