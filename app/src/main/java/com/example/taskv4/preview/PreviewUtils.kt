package com.example.taskv4.preview

import android.app.Application
import com.example.taskv4.Task
import com.example.taskv4.ViewModel.TaskViewModel
import com.example.taskv4.data.TaskRepository
import com.example.taskv4.data.local.TaskDao
import com.example.taskv4.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private class PreviewApplication : Application()

private class InMemoryTaskDao(initial: List<TaskEntity>) : TaskDao {
    private val state = MutableStateFlow(initial)

    override fun getAllFlow(): Flow<List<TaskEntity>> = state.asStateFlow()

    override suspend fun getById(id: Int): TaskEntity? = state.value.find { it.id == id }

    override suspend fun insert(entity: TaskEntity): Long {
        val nextId = (state.value.maxOfOrNull { it.id } ?: 0) + 1
        val toAdd = entity.copy(id = nextId)
        state.value = state.value + toAdd
        return nextId.toLong()
    }

    override suspend fun update(entity: TaskEntity) {
        state.value = state.value.map { if (it.id == entity.id) entity else it }
    }

    override suspend fun deleteById(id: Int) {
        state.value = state.value.filter { it.id != id }
    }

    override suspend fun toggleCompleted(id: Int) {
        state.value = state.value.map { if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it }
    }
}

fun previewTaskViewModel(): TaskViewModel {
    val sample = listOf(
        TaskEntity(1, "Grocery Shopping", "High", "Buy fresh vegetables", "2024-08-15", "2024-08-10", false),
        TaskEntity(2, "Pay Utility Bills", "Medium", "Electricity, water, internet", "2024-08-20", "2024-08-12", false),
        TaskEntity(3, "Finish Report", "High", "Quarterly analysis", "2024-08-14", "2024-08-05", true)
    )
    val dao: TaskDao = InMemoryTaskDao(sample)
    val repository = TaskRepository(dao)
    return TaskViewModel(PreviewApplication(), repository)
}


