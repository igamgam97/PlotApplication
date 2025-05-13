package com.example.myapplication.data.local

import com.example.myapplication.data.model.points.PointConverter
import com.example.myapplication.data.model.points.PointDto
import com.example.myapplication.domain.gateways.PointLocalSource
import com.example.myapplication.domain.model.points.Point
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointLocalSourceImplementation @Inject constructor(
    private val pointConverter: PointConverter,
) : PointLocalSource {
    @Volatile
    var points: List<PointDto> = emptyList()


    override fun savePoints(points: List<PointDto>) {
        this.points = points
    }

    override fun getCachedPoints(): List<Point> {
        return points.map(pointConverter::toBusiness)
    }
}