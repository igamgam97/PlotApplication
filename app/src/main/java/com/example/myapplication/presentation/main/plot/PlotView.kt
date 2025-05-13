package com.example.myapplication.presentation.main.plot

import com.example.myapplication.domain.model.points.Point
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface PlotView : MvpView {
    @AddToEndSingle
    fun showPoints(points: List<Point>)
    @AddToEndSingle
    fun showLoading()
    @AddToEndSingle
    fun hideLoading()
    @Skip
    fun showError(message: String)
    @Skip
    fun showSuccess(message: String)
    @Skip
    fun shareChart(filePath: String)
} 