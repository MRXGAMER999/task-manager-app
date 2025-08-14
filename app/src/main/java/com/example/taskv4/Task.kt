package com.example.taskv4

data class Task(
    val id: Int,
    val title: String,
    val priority: String,
    val description: String,
    val dueDate: String,
    val dateCreated: String,
    var isCompleted: Boolean = false
)
