package com.example.runningtracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningtracker.R
import com.example.runningtracker.db.Running
import com.example.runningtracker.other.TrackingUtil
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RunningAdapter: RecyclerView.Adapter<RunningAdapter.RunningViewHolder>() {
    private lateinit var ivImage : ImageView
    private lateinit var tvDate : TextView
    private lateinit var tvAvgSpeed : TextView
    private lateinit var tvDistance : TextView
    private lateinit var tvTime : TextView
    private lateinit var tvCaloriesBurned : TextView
    inner class RunningViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Running>() {
        override fun areItemsTheSame(oldItem: Running, newItem: Running): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Running, newItem: Running): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list : List<Running>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_running,
            parent,
            false
        )
        ivImage = itemView.findViewById(R.id.ivRunImage)
        tvDate = itemView.findViewById(R.id.tvDate)
        tvAvgSpeed = itemView.findViewById(R.id.tvAvgSpeed)
        tvDistance = itemView.findViewById(R.id.tvDistance)
        tvTime = itemView.findViewById(R.id.tvTime)
        tvCaloriesBurned = itemView.findViewById(R.id.tvCalories)
        return RunningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RunningViewHolder, position: Int) {
        val running = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(running.bitmap).into(ivImage)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = running.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)
            val avgSpeed = "${running.avgSpeed}km/h"
            tvAvgSpeed.text = avgSpeed
            val distance = "${running.distance / 1000f}km"
            tvDistance.text = distance
            tvTime.text = TrackingUtil.getFormattedStopWatch(running.timeInMillis)
            val calories = "${running.calories}kcal"
            tvCaloriesBurned.text = calories
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}