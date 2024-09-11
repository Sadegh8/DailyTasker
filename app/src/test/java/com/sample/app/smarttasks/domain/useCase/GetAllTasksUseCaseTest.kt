package com.sample.app.smarttasks.domain.useCase

import com.sample.app.smarttasks.data.common.Resource
import com.sample.app.smarttasks.data.database.TaskDao
import com.sample.app.smarttasks.data.database.TaskDatabase
import com.sample.app.smarttasks.data.database.TaskEntity
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetAllTasksUseCaseTest {

    @Mock
    private lateinit var repository: TasksRepository

    @Mock
    private lateinit var database: TaskDatabase

    @Mock
    private lateinit var taskDao: TaskDao

    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(database.taskStateDao).thenReturn(taskDao)
        getAllTasksUseCase = GetAllTasksUseCase(repository, database)
    }

    @Test
    fun `invoke should return tasks combined with their states`() = runTest {
        // Arrange
        val tasks = listOf(
            Task(id = "1", targetDate = "2024-08-25", dueDate = "2024-08-31", title = "SDK update", dayLeft = "5", description = "Update Android SDK", priority = "0"),
            Task(id = "2", targetDate = "2024-08-25", dueDate = "2024-09-10", title = "Setup Jenkins", dayLeft = "10", description = "Setup Jenkins environment", priority = "1")
        )

        val taskEntities = listOf(
            TaskEntity(taskId = "1", isResolved = true),
            TaskEntity(taskId = "2", isResolved = false)
        )

        // Stubbing
        `when`(repository.getTasks()).thenReturn(tasks)
        `when`(taskDao.getAllTaskState()).thenReturn(flowOf(taskEntities))

        // Act
        val result: Flow<Resource<List<Task>>> = getAllTasksUseCase()

        // Assert
        result.collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // This is expected, so we can just ignore it
                }
                is Resource.Success -> {
                    val expected = listOf(
                        tasks[0].copy(isResolved = true),
                        tasks[1].copy(isResolved = false)
                    )
                    assertEquals(expected, resource.data)
                }
                is Resource.Error -> {
                    throw AssertionError("Unexpected error state: ${resource.message}")
                }
            }
        }
    }
}
