package com.example.myapplication.data.model.points

import com.example.myapplication.domain.model.points.Point
import javax.inject.Inject

class PointConverter @Inject constructor() {

    fun toBusiness(pointDto: PointDto): Point {
        return Point(pointDto.x, pointDto.y)
    }
}