package com.sinxn.myhabits.domain.model

import java.util.UUID


data class SubTask(
    val title: String,
    val id: UUID = UUID.randomUUID(),
    val isCompleted: Boolean = false

)

