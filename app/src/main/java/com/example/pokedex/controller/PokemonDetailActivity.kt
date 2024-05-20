package com.example.pokedex.controller

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.Singleton
import com.example.pokedex.model.Statistics
import com.example.pokedex.model.UserEntity
import com.example.pokedex.util.Utils
import com.example.pokedex.viewmodel.LoginViewModel
import com.example.pokedex.viewmodel.LoginViewModelFactory
import com.example.pokedex.viewmodel.PokemonDetailViewModel
import com.example.pokedex.viewmodel.PokemonDetailViewModelFactory
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var binding: ActivityPokemonDetailBinding
    private var pokemon: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_pokemon_detail
        )
        initViewModel()
        observeLoadingState()
        observeFavoriteState()
        loadData()
    }

    private fun initViewModel() {
        viewModel = PokemonDetailViewModelFactory().create(PokemonDetailViewModel::class.java)
        val pokemonId = intent.getIntExtra("pokemon_id", 0)
        viewModel.viewModelScope.launch {
            pokemon = viewModel.getPokemonInfo(pokemonId)
            pokemon?.let {
                loadData()
            }
        }
    }

    private fun observeLoadingState() {
        viewModel.loading.observe(this) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun observeFavoriteState() {
        binding.favoriteButton.setOnClickListener{viewModel.toggleFavorite()}
        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.favorite_fill)
                binding.favoriteButton.imageTintList = (ColorStateList.valueOf(Color.parseColor("#FFFF9800")))
            } else {
                binding.favoriteButton.setImageResource(R.drawable.favorite_not_fill)
                binding.favoriteButton.imageTintList = (ColorStateList.valueOf(Color.parseColor("#9E9E9E")))
            }
        }
    }

    private fun loadData() {
        pokemon?.let {
            binding.pokemonName.text = it.name
            Picasso.get().load(it.image).into(binding.pokemonImage)
            binding.body.setBackgroundColor(Color.parseColor(it.typeColor))
            for (type in it.types) {
                val chip = Chip(this)
                chip.text = type
                binding.typeChipGroup.addView(chip)
            }
            binding.valueHeight.text = "${Utils.decimetresToMetres(it.height)} M";
            binding.valueWeight.text = "${Utils.convertHectogramsToKilograms(it.weight)} KG";
            updateRecyclerView(it.stats)
        }
    }

    private fun updateRecyclerView(stats: List<Statistics>) {
        binding.recyclerViewStats.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewStats.adapter = StatsAdapter(stats)
    }
}