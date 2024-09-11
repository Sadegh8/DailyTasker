package com.sample.app.smarttasks.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskState(taskState: TaskEntity)

    @Query("SELECT * FROM task_table WHERE taskId = :taskId")
    fun getTaskState(taskId: String): Flow<TaskEntity?>

    @Query("SELECT * FROM task_table")
    fun getAllTaskState(): Flow<List<TaskEntity>>

}
