package com.sample.app.smarttasks.domain.repository

import com.sample.app.smarttasks.data.database.TaskEntity
import com.sample.app.smarttasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun getTasks(): List<Task>
    suspend fun getTaskState(taskId: String): Flow<TaskEntity?>
    suspend fun updateTaskState(taskId: String, isResolved: Boolean, comment: String?)
}
