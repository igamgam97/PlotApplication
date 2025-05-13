package com.example.myapplication.domain.interactor.points

import android.graphics.Bitmap
import com.example.myapplication.domain.gateways.ChartLocalSource
import io.reactivex.rxjava3.core.Single
import java.io.File
import javax.inject.Inject

class SaveChartUseCase @Inject constructor(
    private val chartLocalSource: ChartLocalSource
) {
    fun execute(bitmap: Bitmap): Single<File> {
        return chartLocalSource.saveChart(bitmap)
    }
} 