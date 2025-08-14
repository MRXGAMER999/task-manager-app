package com.example.taskv4.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.taskv4.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskViewModel : ViewModel() {

    var tasks by mutableStateOf(getSampleTasks())
        private set

    var isSortedByPriority by mutableStateOf(false)
        private set


    fun addTask(title: String, priority: String , description: String, dueDate: String) {
        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormatter.format(Date())

        val newTask = Task(
            id = newId,
            title = title,
            priority = priority,
            description = description,
            dueDate = dueDate,
            dateCreated = currentDate,
            isCompleted = false
        )
        tasks = tasks + newTask
    }


    fun toggleTaskCompletion(taskId: Int) {
        tasks = tasks.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }


    fun deleteTask(taskId: Int) {
        tasks = tasks.filter { it.id != taskId }
    }


    fun updateTask(taskId: Int, title: String? = null, priority: String? = null) {
        tasks = tasks.map { task ->
            if (task.id == taskId) {
                task.copy(
                    title = title ?: task.title,
                    priority = priority ?: task.priority
                )
            } else {
                task
            }
        }
    }


    fun getTask(taskId: Int): Task? {
        return tasks.find { it.id == taskId }
    }


    fun getCompletedTasks(): List<Task> {
        return tasks.filter { it.isCompleted }
    }

    fun getPendingTasks(): List<Task> {
        return tasks.filter { !it.isCompleted }
    }


    fun getTasksByPriority(priority: String): List<Task> {
        return tasks.filter { it.priority.equals(priority, ignoreCase = true) }
    }

    fun toggleSortByPriority() {
        isSortedByPriority = !isSortedByPriority
        if (isSortedByPriority) {
            sortTasksByPriority()
        } else {
            // Reset to original order (by ID)
            tasks = tasks.sortedBy { it.id }
        }
    }

    private fun sortTasksByPriority() {
        val priorityOrder = mapOf("High" to 1, "Medium" to 2, "Low" to 3)
        tasks = tasks.sortedBy { priorityOrder[it.priority] ?: 4 }
    }

    private fun getSampleTasks(): List<Task> {
        return listOf(
            Task(1, "Grocery Shopping", "High", "Buy fresh vegetables, fruits, and weekly essentials from the supermarket", "2024-08-15", "2024-08-10", false),
            Task(2, "Pay Utility Bills", "Medium", "Pay electricity, water, and internet bills before the due date", "2024-08-20", "2024-08-12", false),
            Task(3, "Book Doctor's Appointment", "Low", "Schedule annual checkup with family physician for next month", "2024-08-25", "2024-08-11", false),
            Task(4, "Finish Project Report", "High", "Complete the quarterly analysis report for the marketing team", "2024-08-14", "2024-08-05", true),
            Task(5, "Evening Gym Workout", "Medium", "45-minute cardio session followed by strength training", "2024-08-16", "2024-08-13", false),
            Task(6, "Read 'Atomic Habits'", "Low", "Read chapter 5-7 of the productivity book", "2024-08-30", "2024-08-08", false),
            Task(7, "Prepare Presentation", "High", "Create slides for Monday's client meeting on new product features", "2024-08-19", "2024-08-09", false),
            Task(8, "Call Mom", "Medium", "Catch up with family and discuss weekend plans", "2024-08-17", "2024-08-13", false),
            Task(9, "Clean Garage", "Low", "Organize tools and donate unused items to charity", "2024-09-01", "2024-08-07", false),
            Task(10, "Learn Spanish", "Low", "Complete lesson 12 on Duolingo and practice pronunciation", "2024-08-22", "2024-08-06", false),
            Task(11, "Update Resume", "Medium", "Add recent projects and certifications to LinkedIn profile", "2024-08-28", "2024-08-04", false),
            Task(12, "Plan Vacation", "Medium", "Research hotels and activities for summer trip to Italy", "2024-09-05", "2024-08-03", false),
            Task(13, "Fix Kitchen Faucet", "High", "Replace the leaking washer and check water pressure", "2024-08-18", "2024-08-12", false),
            Task(14, "Write Blog Post", "Low", "Draft article about sustainable living practices", "2024-09-10", "2024-08-02", false),
            Task(15, "Team Building Event", "Medium", "Organize monthly team lunch at the new restaurant downtown", "2024-08-26", "2024-08-01", false)
        )
    }
}