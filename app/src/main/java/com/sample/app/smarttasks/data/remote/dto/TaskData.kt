package com.sample.app.smarttasks.data.remote.dto

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.utils.Helper

data class TaskData(val tasks: List<TaskItems>)

data class TaskItems(
    val id: String,
    @SerializedName("TargetDate") val targetDate: String,
    @SerializedName("DueDate") val dueDate: String?,
    @SerializedName("Title") val title: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Priority") val priority: Int,
)

fun TaskData.toTasks(): List<Task> {
    val helperDate = Helper
    Log.d("TAG Sadegh", "toTasks: $this")
    return this.tasks.map {
        Task(
            id = it.id,
            targetDate = it.targetDate,
            dueDate = it.dueDate,
            title = it.title,
            description = it.description,
            dayLeft = helperDate.daysLeft(it.targetDate, it.dueDate),
            priority = it.priority.toString(),
        )
    }.toList()
}
