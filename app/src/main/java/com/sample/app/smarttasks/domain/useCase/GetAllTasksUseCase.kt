package com.sample.app.smarttasks.domain.useCase

import android.util.Log
import com.sample.app.smarttasks.data.common.Resource
import com.sample.app.smarttasks.data.database.TaskDatabase
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val repository: TasksRepository,
    private val database: TaskDatabase,
) {

    operator fun invoke(): Flow<Resource<List<Task>>> = flow {
        emit(Resource.Loading())

        try {
            val tasks = repository.getTasks().sortedBy { it.priority }

            database.taskStateDao.getAllTaskState().collect { taskStatesFromDb ->
                val combinedTasks = tasks.map { task ->
                    val taskState = taskStatesFromDb.find { it.taskId == task.id }
                    task.copy(isResolved = taskState?.isResolved)
                }

                emit(Resource.Success(combinedTasks))
            }
        } catch (e: HttpException) {
            Log.e("GetAllTasksUseCase", e.localizedMessage ?: "An unexpected error occurred!")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            Log.e(
                "GetAllTasksUseCase",
                e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
            )
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }.catch { e ->
        Log.e("GetAllTasksUseCase", e.localizedMessage ?: "An unexpected error occurred!")
        emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred!"))
    }
}
