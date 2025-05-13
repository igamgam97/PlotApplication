package com.example.myapplication.di

import com.example.myapplication.navigation.NavigationHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    
    @Provides
    @Singleton
    fun provideRouter(navigationHolder: NavigationHolder): Router {
        return navigationHolder.router
    }
} 