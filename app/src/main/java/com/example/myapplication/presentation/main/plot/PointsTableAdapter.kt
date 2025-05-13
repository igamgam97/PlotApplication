package com.example.myapplication.presentation.main.plot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPointBinding
import com.example.myapplication.domain.model.points.Point

class PointsTableAdapter : RecyclerView.Adapter<PointsTableAdapter.PointViewHolder>() {
    private var points: List<Point> = emptyList()

    fun setPoints(newPoints: List<Point>) {
        points = newPoints
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val binding = ItemPointBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(points[position])
    }

    override fun getItemCount(): Int = points.size

    class PointViewHolder(
        private val binding: ItemPointBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(point: Point) {
            binding.xValue.text = "X: %.2f".format(point.x)
            binding.yValue.text = "Y: %.2f".format(point.y)
        }
    }
} 