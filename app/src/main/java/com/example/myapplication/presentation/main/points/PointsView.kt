package com.example.myapplication.presentation.main.points

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface PointsView : MvpView {
    @AddToEndSingle
    fun showLoading()

    @AddToEndSingle
    fun hideLoading()

    @Skip
    fun showInputError(message: String)

    @Skip
    fun showError(message: String)
}