package com.example.myapplication.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationHolder @Inject constructor() {
    private val cicerone: Cicerone<Router> = Cicerone.create()
    
    val router: Router
        get() = cicerone.router
        
    val navigatorHolder: NavigatorHolder
        get() = cicerone.getNavigatorHolder()
} 