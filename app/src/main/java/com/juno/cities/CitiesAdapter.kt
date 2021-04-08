package com.juno.cities

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juno.cities.databinding.ItemCityBinding

class CitiesAdapter(
    private val context: Context,
    private val onClick: (Package) -> Unit
) : ListAdapter<Package, CitiesAdapter.ViewHolder>(SearchDiff()) {
    inner class ViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: Package) {
            binding.local.setOnClickListener {
                onClick(city)
            }
            binding.preco.setOnClickListener {
                onClick(city)
            }
            binding.dias.setOnClickListener {
                onClick(city)
            }

            binding.local.text = city.local
            binding.preco.text = city.preco
            binding.dias.text = city.dias
        }
    }

    class SearchDiff : DiffUtil.ItemCallback<Package>() {
        override fun areItemsTheSame(oldItem: Package, newItem: Package): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Package, newItem: Package): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}