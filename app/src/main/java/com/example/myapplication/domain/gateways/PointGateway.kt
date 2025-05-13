package com.example.myapplication.domain.gateways

import io.reactivex.rxjava3.core.Completable

interface PointGateway {
    fun getPoints(count: Int): Completable
} 