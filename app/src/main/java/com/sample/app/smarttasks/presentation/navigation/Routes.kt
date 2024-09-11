package com.sample.app.smarttasks.presentation.navigation

import com.sample.app.smarttasks.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
object Tasks

data class Details(
    val task: Task,
)