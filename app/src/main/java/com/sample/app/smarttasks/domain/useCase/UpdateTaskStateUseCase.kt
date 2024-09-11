package com.sample.app.smarttasks.domain.useCase


import com.sample.app.smarttasks.domain.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTaskStateUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(id: String, state: Boolean, comment: String?) {
        withContext(Dispatchers.IO) {
            tasksRepository.updateTaskState(id, state, comment = comment)
        }
    }
}
