package com.sample.app.smarttasks.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.app.smarttasks.domain.model.TaskExtra

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey val taskId: String,
    val isResolved: Boolean,
    val comment: String? = null,
)

fun TaskEntity.toTaskExtra(): TaskExtra {
    return TaskExtra(
        taskId = taskId,
        isResolved = isResolved,
        comment = comment,
    )
}
