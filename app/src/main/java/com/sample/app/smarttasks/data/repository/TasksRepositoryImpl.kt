package com.sample.app.smarttasks.data.repository

import com.sample.app.smarttasks.data.database.TaskDatabase
import com.sample.app.smarttasks.data.database.TaskEntity
import com.sample.app.smarttasks.data.remote.TaskApi
import com.sample.app.smarttasks.data.remote.dto.toTasks
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val database: TaskDatabase,
) : TasksRepository {

    override suspend fun getTasks(): List<Task> {
        return api.getListAllTasks().toTasks()
    }

    override suspend fun getTaskState(taskId: String): Flow<TaskEntity?> {
       return database.taskStateDao.getTaskState(taskId)
    }

    override suspend fun updateTaskState(taskId: String, isResolved: Boolean, comment: String?) {
        val taskState = TaskEntity(taskId = taskId, isResolved = isResolved, comment = comment)
        database.taskStateDao.insertTaskState(taskState)
    }
}
