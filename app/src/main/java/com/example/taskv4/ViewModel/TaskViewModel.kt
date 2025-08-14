package com.example.taskv4.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskv4.Task
import com.example.taskv4.data.TaskRepository
import com.example.taskv4.data.local.AppDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

class TaskViewModel(application: Application, private val repository: TaskRepository? = null) : AndroidViewModel(application) {

    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    var isSortedByPriority by mutableStateOf(false)
        private set

    private val repo: TaskRepository = repository ?: TaskRepository(
        AppDatabase.getInstance(application).taskDao()
    )

    init {
        viewModelScope.launch {
            repo.allTasksFlow.collect { list ->
                tasks = if (isSortedByPriority) {
                    sortListByPriority(list)
                } else {
                    list.sortedBy { it.id }
                }
            }
        }
    }

    fun addTask(title: String, priority: String, description: String, dueDate: String) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormatter.format(Date())

        val newTask = Task(
            id = 0,
            title = title,
            priority = priority,
            description = description,
            dueDate = dueDate,
            dateCreated = currentDate,
            isCompleted = false
        )
        viewModelScope.launch { repo.addTask(newTask) }
    }

    fun toggleTaskCompletion(taskId: Int) {
        viewModelScope.launch { repo.toggleCompleted(taskId) }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch { repo.deleteTask(taskId) }
    }

    fun updateTask(
        taskId: Int,
        title: String? = null,
        priority: String? = null,
        description: String? = null,
        dueDate: String? = null
    ) {
        viewModelScope.launch {
            val existing = repo.getTask(taskId) ?: return@launch
            val updated = existing.copy(
                title = title ?: existing.title,
                priority = priority ?: existing.priority,
                description = description ?: existing.description,
                dueDate = dueDate ?: existing.dueDate
            )
            repo.updateTask(updated)
        }
    }

    fun getTask(taskId: Int): Task? {
        return tasks.find { it.id == taskId }
    }

    fun getCompletedTasks(): List<Task> = tasks.filter { it.isCompleted }

    fun getPendingTasks(): List<Task> = tasks.filter { !it.isCompleted }

    fun getTasksByPriority(priority: String): List<Task> =
        tasks.filter { it.priority.equals(priority, ignoreCase = true) }

    fun toggleSortByPriority() {
        isSortedByPriority = !isSortedByPriority
        tasks = if (isSortedByPriority) {
            sortListByPriority(tasks)
        } else {
            tasks.sortedBy { it.id }
        }
    }

    private fun sortListByPriority(list: List<Task>): List<Task> {
        val priorityOrder = mapOf("High" to 1, "Medium" to 2, "Low" to 3)
        return list.sortedBy { priorityOrder[it.priority] ?: 4 }
    }
}