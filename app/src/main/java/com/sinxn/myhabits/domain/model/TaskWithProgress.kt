package com.sinxn.myhabits.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithProgresses(
    @Embedded var task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id"
    )
    var progress: List<Progress>
)

data class TaskAndProgress(
    @Embedded var task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id"
    )
    var progress: List<Progress>
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
    @ColumnInfo(name = "due_date")
    val dueDate: Long = 0L,
    @ColumnInfo(name = "sub_tasks")
    val subTasks: List<SubTask> = emptyList(),
    val interval: Int = 0,
    val remainder: Boolean = false,
    @Embedded var progress: Progress?
)

