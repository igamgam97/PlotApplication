package com.example.myapplication.domain.gateways

import com.example.myapplication.data.model.points.PointDto
import com.example.myapplication.domain.model.points.Point

interface PointLocalSource {
    fun savePoints(points: List<PointDto>)
    fun getCachedPoints():  List<Point>
}