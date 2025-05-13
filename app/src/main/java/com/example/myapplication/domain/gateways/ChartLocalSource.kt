package com.example.myapplication.domain.gateways

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Single
import java.io.File

interface ChartLocalSource {
    fun saveChart(bitmap: Bitmap): Single<File>
} 