package com.example.myapplication.data.network.points

import com.example.myapplication.data.network.api.PointApiService
import com.example.myapplication.domain.gateways.PointGateway
import com.example.myapplication.domain.gateways.PointLocalSource
import com.example.myapplication.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class PointGatewayImplementation @Inject constructor(
    private val apiService: PointApiService,
    private val pointLocalSource: PointLocalSource,
    private val schedulerProvider: SchedulerProvider,
) : PointGateway {
    override fun getPoints(count: Int): Completable {
        return apiService.getPoints(count)
            .map { response -> response.points.sortedBy { it.x } }
            .flatMapCompletable { points ->
                if (points.isEmpty()) {
                    // Возвращаем ошибку, если список пустой
                    Completable.error(Throwable("Пустой список точек"))
                } else {
                    Completable.fromAction {
                        pointLocalSource.savePoints(points)
                    }
                }
            }
            .subscribeOn(schedulerProvider.io())
    }
} 