package com.example.myapplication.data.model.points

import com.google.gson.annotations.SerializedName

data class PointDto(
    @SerializedName("x")
    val x: Double,

    @SerializedName("y")
    val y: Double
)

data class PointsResponseDto(
    @SerializedName("points")
    val points: List<PointDto>
)