package com.sample.app.smarttasks.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val targetDate: String,
    val dueDate: String?,
    val title: String,
    val dayLeft: String,
    val description: String,
    val priority: String,
    val isResolved: Boolean? = false,
)
