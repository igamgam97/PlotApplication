package com.example.myapplication.data.network.api

import com.example.myapplication.data.model.points.PointsResponseDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PointApiService {
    @GET("points")
    fun getPoints(@Query("count") count: Int): Single<PointsResponseDto>
} 