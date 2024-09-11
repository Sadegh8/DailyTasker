package com.sample.app.smarttasks.di

import com.sample.app.smarttasks.data.repository.TasksRepositoryImpl
import com.sample.app.smarttasks.domain.repository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsTasksRepository(repositoryImpl: TasksRepositoryImpl): TasksRepository
}
