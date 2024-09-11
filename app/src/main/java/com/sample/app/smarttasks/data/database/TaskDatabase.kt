package com.sample.app.smarttasks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskStateDao: TaskDao
}
