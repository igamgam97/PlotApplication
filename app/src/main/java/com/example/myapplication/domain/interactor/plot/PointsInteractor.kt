package com.example.myapplication.domain.interactor.plot

import com.example.myapplication.domain.gateways.PointGateway
import com.example.myapplication.domain.gateways.PointLocalSource
import com.example.myapplication.domain.model.points.Point
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class PointsInteractor @Inject constructor(
    private val pointGateway: PointGateway,
    private val pointLocalSource: PointLocalSource,
) {
    fun getPoints(count: Int): Completable {
        return pointGateway.getPoints(count)
    }

    fun getCachePoints(): List<Point> {
        return pointLocalSource.getCachedPoints()
    }
} 