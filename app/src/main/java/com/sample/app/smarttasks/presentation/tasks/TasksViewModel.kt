package com.sample.app.smarttasks.presentation.tasks

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.app.smarttasks.data.common.Resource
import com.sample.app.smarttasks.domain.useCase.GetAllTasksUseCase
import com.sample.app.smarttasks.presentation.tasks.data.TasksState
import com.sample.app.smarttasks.utils.Helper.parseDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
) : ViewModel() {

    var tasksState by mutableStateOf(TasksState())
        private set

    var selectedDate: LocalDate by mutableStateOf(LocalDate.now())
    private set
    private var availableDates by mutableStateOf<List<LocalDate>>(emptyList())

    init {
        fetchTasksForDate(selectedDate)
    }

    @VisibleForTesting
    fun fetchTasksForDate(date: LocalDate) {
        val dateTitle = if (date == LocalDate.now()) "Today" else date.toString()
        getAllTasksUseCase().onEach { result ->
            tasksState = when (result) {
                is Resource.Success -> {
                    val tasks = result.data ?: emptyList()
                    val tasksForDate = result.data?.filter { task ->
                        val taskDate = parseDate(task.dueDate)
                        taskDate == date
                    } ?: emptyList()

                    // Extract available dates from tasks
                    val datesWithTasks = tasks
                        .mapNotNull { parseDate(it.dueDate) }
                        .distinct()
                        .sorted()

                    availableDates = datesWithTasks

                    when {
                        tasksForDate.isEmpty() -> {
                            TasksState(
                                emptyState = true,
                                dateTitle = dateTitle
                            )
                        }
                        else -> {
                            TasksState(
                                tasks = tasksForDate,
                                dateTitle = dateTitle
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    TasksState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    tasksState.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun goToNextDay() {
        val nextDate = availableDates.find { it.isAfter(selectedDate) }
        if (nextDate != null) {
            selectedDate = nextDate
            fetchTasksForDate(selectedDate)
        }
    }

    fun goToPreviousDay() {
        val previousDate = availableDates.reversed().find { it.isBefore(selectedDate) }
        if (previousDate != null) {
            selectedDate = previousDate
            fetchTasksForDate(selectedDate)
        }
    }
}
