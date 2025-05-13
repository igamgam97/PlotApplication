package com.example.myapplication.presentation.main.plot

import android.graphics.Bitmap
import com.example.myapplication.domain.interactor.plot.PointsInteractor
import com.example.myapplication.domain.interactor.points.SaveChartUseCase
import com.example.myapplication.scheduler.SchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class PlotPresenter @Inject constructor(
    private val saveChartUseCase: SaveChartUseCase,
    private val pointsInteractor: PointsInteractor,
    private val schedulerProvider: SchedulerProvider,
) : MvpPresenter<PlotView>() {

    private val disposables = CompositeDisposable()

    fun initialize() {
        viewState.showPoints(pointsInteractor.getCachePoints())
    }

    fun saveChart(bitmap: Bitmap) {
        viewState.showLoading()
        disposables.add(
            saveChartUseCase.execute(bitmap)
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { file ->
                        viewState.hideLoading()
                        viewState.showSuccess("Chart saved successfully")
                        viewState.shareChart(file.absolutePath)
                    },
                    { error ->
                        viewState.hideLoading()
                        viewState.showError(error.message ?: "Failed to save chart")
                    }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
} 