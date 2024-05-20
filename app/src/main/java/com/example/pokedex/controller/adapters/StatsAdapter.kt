package com.example.pokedex.controller.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.StatisticListRowBinding
import com.example.pokedex.model.response.domain.Statistics

class StatsAdapter(private val stats: List<Statistics>) : RecyclerView.Adapter<StatsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: StatisticListRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(stat: Statistics?){
            stat?.let {
                binding.textViewStatName.text = "${it.name} - ${it.value}"
                binding.progressBarStat.progress = it.value
                binding.progressBarStat.progressTintList = ColorStateList.valueOf(Color.parseColor(it.color))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StatisticListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stats[position]
        holder.bind(item)
    }

    override fun getItemCount() = stats.size
}