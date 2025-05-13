package com.example.myapplication.di

import com.example.myapplication.data.local.ChartLocalSourceImplementation
import com.example.myapplication.domain.gateways.ChartLocalSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChartModule {

    @Binds
    @Singleton
    abstract fun bindChartRepository(
        chartLocalSourceImplementation: ChartLocalSourceImplementation
    ): ChartLocalSource
}