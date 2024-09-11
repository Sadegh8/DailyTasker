package com.sample.app.smarttasks.data.remote

import com.sample.app.smarttasks.data.remote.dto.TaskData
import retrofit2.http.GET

interface TaskApi {

    @GET("/")
    suspend fun getListAllTasks(): TaskData
}
