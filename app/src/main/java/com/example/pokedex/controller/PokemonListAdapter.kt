package com.example.pokedex.controller

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonListRowBinding
import com.example.pokedex.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonListAdapter(private val items: List<Pokemon?>, val clickListener: OnCityClickListener) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    interface OnCityClickListener{
        fun onPokemonClick(view: View, position: Int)
    }

    inner class ViewHolder(private val binding: PokemonListRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: Pokemon?){
            pokemon?.let {
                binding.pokemonName.text = it.name
                Picasso.get().load(it.image).into(binding.pokemonImage)
                binding.pokemonBorderColor.setBackgroundColor(Color.parseColor(it.typeColor))
            }
            binding.root.setOnClickListener{
                clickListener.onPokemonClick(it,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokemonListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}
