package com.sample.app.smarttasks.presentation.tasks.data

import com.sample.app.smarttasks.domain.model.Task

data class TasksState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val icon: Int? = null,
    val dateTitle: String = "Today",
    val error: String = "",
    val emptyState: Boolean = false,
)
