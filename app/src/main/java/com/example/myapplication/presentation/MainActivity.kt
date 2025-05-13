package com.example.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.navigation.NavigationHolder
import com.example.myapplication.navigation.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    @Inject
    lateinit var navigationHolder: NavigationHolder
    
    private val navigator by lazy { AppNavigator(this, R.id.fragmentContainer) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigationHolder.router.newRootScreen(Screen.Home())
        }
    }
    
    override fun onResume() {
        super.onResume()
        navigationHolder.navigatorHolder.setNavigator(navigator)
    }
    
    override fun onPause() {
        navigationHolder.navigatorHolder.removeNavigator()
        super.onPause()
    }
} 