package com.sample.app.smarttasks.presentation.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.app.smarttasks.data.common.Resource
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.domain.useCase.GetAllTasksUseCase
import com.sample.app.smarttasks.presentation.tasks.data.TasksState
import com.sample.app.smarttasks.utils.Helper.parseDate
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After

@ExperimentalCoroutinesApi
class TasksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

    private lateinit var viewModel: TasksViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TasksViewModel(getAllTasksUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `fetchTasksForDate should update tasksState with loading state when fetching`() {
        // Arrange
        val successData = listOf(createDummyTask())

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Loading(data = successData)))

        // Act
        viewModel.fetchTasksForDate(LocalDate.now())

        // Assert
        val expectedState = TasksState(isLoading = true)
        assertEquals(expectedState, viewModel.tasksState)
    }


    @Test
    fun `fetchTasksForDate should update tasksState with success state and tasks when successful`() {
        // Arrange
        val successData = listOf(createDummyTask())
        val expectedDate = LocalDate.now()

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Success(successData)))

        // Act
        viewModel.fetchTasksForDate(expectedDate)

        // Assert
        val tasksForDate = successData.filter { task ->
            parseDate(task.dueDate) == expectedDate
        }

        val expectedState = TasksState(
            tasks = tasksForDate,
            dateTitle = "Today",
        )
        assertEquals(expectedState, viewModel.tasksState)
    }

    @Test
    fun `fetchTasksForDate should update tasksState with empty state when no tasks for date`() {
        // Arrange
        val successData = emptyList<Task>()

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Success(successData)))

        // Act
        viewModel.fetchTasksForDate(LocalDate.now())

        // Assert
        val expectedState = TasksState(
            emptyState = true,
            dateTitle = "Today"
        )
        assertEquals(expectedState, viewModel.tasksState)
    }

    @Test
    fun `fetchTasksForDate should update tasksState with error state on use case error`() {
        // Arrange
        val errorMessage = "Network Error"

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))

        // Act
        viewModel.fetchTasksForDate(LocalDate.now())

        // Assert
        val expectedState = TasksState(error = errorMessage)
        assertEquals(expectedState, viewModel.tasksState)
    }

    @Test
    fun `goToNextDay should update selectedDate and fetch tasks for new date`() {
        // Arrange
        val nextDate = LocalDate.now().plusDays(1)
        val tasks = listOf(createDummyTask(dueDate = nextDate.toString()))

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Success(tasks)))

        viewModel.fetchTasksForDate(LocalDate.now())

        // Act
        viewModel.goToNextDay()

        // Assert
        assertEquals(nextDate, viewModel.selectedDate)
    }

    @Test
    fun `goToPreviousDay should update selectedDate and fetch tasks for new date`() {
        // Arrange
        val previousDate = LocalDate.now().minusDays(1)
        val tasks = listOf(createDummyTask(dueDate = previousDate.toString()))

        `when`(getAllTasksUseCase()).thenReturn(flowOf(Resource.Success(tasks)))

        viewModel.fetchTasksForDate(LocalDate.now())

        // Act
        viewModel.goToPreviousDay()

        // Assert
        assertEquals(previousDate, viewModel.selectedDate)
    }


    private fun createDummyTask(dueDate: String? = null): Task {
        return Task(
            id = "1",
            targetDate = LocalDate.now().toString(),
            dueDate = dueDate ?: LocalDate.now().toString(),
            title = "Test Task",
            dayLeft = "2",
            description = "test",
            priority = "5"
        )
    }
}
