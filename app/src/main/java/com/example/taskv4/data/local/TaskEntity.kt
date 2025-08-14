package com.example.taskv4.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val priority: String,
    val description: String,
    val dueDate: String,
    val dateCreated: String,
    val isCompleted: Boolean = false
)


