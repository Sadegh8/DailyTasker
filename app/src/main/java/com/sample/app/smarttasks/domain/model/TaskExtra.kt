package com.sample.app.smarttasks.domain.model

data class TaskExtra(
    val taskId: String,
    val isResolved: Boolean,
    val comment: String? = null,
)