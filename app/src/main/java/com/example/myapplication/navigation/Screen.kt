package com.example.myapplication.navigation

import com.example.myapplication.presentation.main.plot.PlotFragment
import com.example.myapplication.presentation.main.points.PointsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {

    fun Home() = FragmentScreen { PointsFragment.newInstance() }
    fun Plot() = FragmentScreen { PlotFragment.newInstance() }
} 