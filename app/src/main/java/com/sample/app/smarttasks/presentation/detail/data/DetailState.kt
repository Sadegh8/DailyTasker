package com.sample.app.smarttasks.presentation.detail.data

data class DetailState(
    val isLoading: Boolean = false,
    val taskState: Boolean? = null,
    val taskComment: String = "",
    val error: String = ""
)
