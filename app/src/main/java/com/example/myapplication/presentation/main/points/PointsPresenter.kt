package com.example.myapplication.presentation.main.points

import com.example.myapplication.domain.interactor.plot.PointsInteractor
import com.example.myapplication.navigation.Screen
import com.example.myapplication.scheduler.SchedulerProvider
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class PointsPresenter @Inject constructor(
    private val pointsInteractor: PointsInteractor,
    private val router: Router,
    private val schedulerProvider: SchedulerProvider,
) : MvpPresenter<PointsView>() {
    private val disposables = CompositeDisposable()

    fun onFetchPointsClick(count: Int?) {
        if (count == null || count < 1 || count > 1000) {
            //TODO: igamgam97 добавить ErrorMessageFactory
            viewState.showInputError("Please enter a number between 1 and 1000")
            return
        }

        viewState.showLoading()
        disposables.add(
            pointsInteractor.getPoints(count)
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        viewState.hideLoading()
                        router.navigateTo(Screen.Plot())
                    },
                    { error ->
                        viewState.hideLoading()
                        viewState.showError(error.message ?: "Unknown error occurred")
                    }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
} 