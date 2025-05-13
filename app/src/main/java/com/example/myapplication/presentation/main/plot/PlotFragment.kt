package com.example.myapplication.presentation.main.plot

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPointsTableBinding
import com.example.myapplication.domain.model.points.Point
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class PlotFragment : MvpAppCompatFragment(), PlotView {

    companion object {

        fun newInstance(): PlotFragment {
            return PlotFragment()
        }
    }

    private var _binding: FragmentPointsTableBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null")
    private val pointsTableAdapter = PointsTableAdapter()

    @Inject
    lateinit var presenterProvider: Provider<PlotPresenter>

    @InjectPresenter
    lateinit var presenter: PlotPresenter

    @ProvidePresenter
    fun providePresenter(): PlotPresenter {
        return presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointsTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupChart()
        setupSaveButton()
    }

    private fun setupRecyclerView() {
        binding.pointsTable.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pointsTableAdapter
        }
    }

    private fun setupChart() {
        binding.pointsChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            // Enable auto scaling
            isAutoScaleMinMaxEnabled = true

            // Configure zoom behavior
            setScaleMinima(1f, 1f)
            isScaleXEnabled = true
            isScaleYEnabled = true

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                labelRotationAngle = -45f
            }

            axisRight.isEnabled = false

            axisLeft.apply {
                setDrawGridLines(true)
                gridColor = Color.LTGRAY
                granularity = 1f
            }

            legend.isEnabled = true

            // Enable smooth scrolling
            isDragDecelerationEnabled = true
            setDragDecelerationFrictionCoef(0.9f)
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val bitmap = binding.pointsChart.chartBitmap
            presenter.saveChart(bitmap)
        }
    }

    override fun showPoints(points: List<Point>) {
        pointsTableAdapter.setPoints(points)
        updateChart(points)
    }

    override fun showLoading() {
        binding.saveButton.isEnabled = false
        binding.saveButton.setText(R.string.saving)
    }

    override fun hideLoading() {
        binding.saveButton.isEnabled = true
        binding.saveButton.setText(R.string.save_chart)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun shareChart(filePath: String) {
        try {
            val file = java.io.File(filePath)
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(
                Intent.createChooser(
                    shareIntent,
                    requireContext().getString(R.string.share_chart),
                )
            )
        } catch (e: Exception) {
            showError("${requireContext().getString(R.string.failed_share_plot)} ${e.message}")
        }
    }

    private fun updateChart(points: List<Point>) {
        if (_binding == null) return

        val sortedPoints = points.sortedBy { it.x }
        val entries = sortedPoints.map { Entry(it.x.toFloat(), it.y.toFloat()) }

        val dataSet = LineDataSet(entries, "Points").apply {
            color = Color.BLUE
            setDrawCircles(true)
            setDrawValues(false)
            lineWidth = 2f
            circleRadius = 4f
            circleColors = listOf(Color.BLUE)

            // Enable smooth curve
            mode = LineDataSet.Mode.CUBIC_BEZIER

            // Add gradient fill
            setDrawFilled(true)
            fillColor = Color.BLUE
            fillAlpha = 50
        }

        binding.pointsChart.apply {
            data = LineData(dataSet)

            // Set custom labels for x-axis
            xAxis.valueFormatter = IndexAxisValueFormatter(sortedPoints.map { it.x.toString() })

            // Adjust viewport to show all points with padding
            val xMin = sortedPoints.first().x.toFloat()
            val xMax = sortedPoints.last().x.toFloat()
            val xRange = xMax - xMin
            setVisibleXRange(xRange * 1.1f, xRange * 1.1f)

            // Center the viewport
            moveViewToX(xMin - xRange * 0.05f)

            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}