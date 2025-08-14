package com.example.taskv4.data

import com.example.taskv4.Task
import com.example.taskv4.data.local.TaskDao
import com.example.taskv4.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {

    val allTasksFlow: Flow<List<Task>> = taskDao.getAllFlow().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun addTask(task: Task) {
        taskDao.insert(task.toEntity())
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task.toEntity())
    }

    suspend fun deleteTask(id: Int) {
        taskDao.deleteById(id)
    }

    suspend fun toggleCompleted(id: Int) {
        taskDao.toggleCompleted(id)
    }

    suspend fun getTask(id: Int): Task? = taskDao.getById(id)?.toDomain()
}

private fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    priority = priority,
    description = description,
    dueDate = dueDate,
    dateCreated = dateCreated,
    isCompleted = isCompleted
)

private fun Task.toEntity(): TaskEntity = TaskEntity(
    id = if (id == 0) 0 else id,
    title = title,
    priority = priority,
    description = description,
    dueDate = dueDate,
    dateCreated = dateCreated,
    isCompleted = isCompleted
)


