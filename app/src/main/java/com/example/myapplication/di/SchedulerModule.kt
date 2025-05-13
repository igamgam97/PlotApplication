package com.example.myapplication.di

import com.example.myapplication.scheduler.DefaultSchedulerProviderImplementation
import com.example.myapplication.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = DefaultSchedulerProviderImplementation()
}