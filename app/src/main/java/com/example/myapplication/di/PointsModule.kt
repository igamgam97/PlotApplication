package com.example.myapplication.di

import com.example.myapplication.data.local.PointLocalSourceImplementation
import com.example.myapplication.data.network.points.PointGatewayImplementation
import com.example.myapplication.domain.gateways.PointGateway
import com.example.myapplication.domain.gateways.PointLocalSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PointsModule {
    @Binds
    @Singleton
    abstract fun bindPointGateway(
        pointRepositoryImpl: PointGatewayImplementation
    ): PointGateway

    @Binds
    @Singleton
    abstract fun bindPointLocalSource(
        pointLocalSourceImpl: PointLocalSourceImplementation
    ): PointLocalSource
} 