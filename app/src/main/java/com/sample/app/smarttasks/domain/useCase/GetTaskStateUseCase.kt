package com.sample.app.smarttasks.domain.useCase

import com.sample.app.smarttasks.data.database.toTaskExtra
import com.sample.app.smarttasks.domain.model.TaskExtra
import com.sample.app.smarttasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTaskStateUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    suspend fun invoke(id: String): Flow<TaskExtra?> = flow {
        tasksRepository.getTaskState(id).collect {
            emit(it?.toTaskExtra())
        }
    }
}
