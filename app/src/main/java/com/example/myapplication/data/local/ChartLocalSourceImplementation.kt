package com.example.myapplication.data.local

import android.content.Context
import android.graphics.Bitmap
import com.example.myapplication.domain.gateways.ChartLocalSource
import com.example.myapplication.scheduler.SchedulerProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ChartLocalSourceImplementation @Inject constructor(
    @ApplicationContext private val context: Context,
    private val schedulerProvider: SchedulerProvider,
) : ChartLocalSource {

    companion object {
        private const val FILE_NAME = "chart.png"
    }

    override fun saveChart(bitmap: Bitmap): Single<File> {
        return Single.fromCallable {
            val file = File(context.cacheDir, FILE_NAME)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            file
        }.subscribeOn(schedulerProvider.io())
    }
}