package com.example.pokedex.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonListRowBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import com.squareup.picasso.Picasso

class PokemonListAdapter(private val items: List<Pokemon?>) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
     inner class ViewHolder(private val binding: PokemonListRowBinding)
         :RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: Pokemon?){
//            Picasso.get().load(pokemon.sprites.front_default).into(binding.pokemonImage)
            pokemon?.let {
                binding.pokemonName.text = it.name
            }
        }
    }
    private var counter = 0
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val binding = PokemonListRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        counter++
        Log.d("Adapter","Created:$counter")
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val item = items[position]
        holder.bind(item)

        Log.d("Adapter","Bind:$position")
    }
    override fun getItemCount() = items.size
}