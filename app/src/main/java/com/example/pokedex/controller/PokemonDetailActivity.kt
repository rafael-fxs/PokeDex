package com.example.pokedex.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.viewmodel.PokemonDetailViewModel
import com.example.pokedex.viewmodel.PokemonDetailViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class PokemonDetailActivity : AppCompatActivity() {
    lateinit var viewModel: PokemonDetailViewModel
    lateinit var binding: ActivityPokemonDetailBinding
    private var pokemon: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_pokemon_detail
        )

        initViewModel()
        loadData()
    }

    private fun initViewModel() {
        viewModel = PokemonDetailViewModelFactory().create(PokemonDetailViewModel::class.java)
        viewModel.viewModelScope.launch {
            pokemon = viewModel.getPokemonInfo(1)
            pokemon?.let {
                loadData()
            }
        }
    }

    private fun loadData() {
        pokemon?.let {
            binding.pokemonName.text = it.name
            Picasso.get().load(it.image).into(binding.pokemonImage)
        }
    }
}