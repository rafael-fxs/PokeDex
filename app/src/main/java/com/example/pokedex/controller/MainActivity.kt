package com.example.pokedex.controller

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: PokemonViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = PokemonViewModelFactory().create(PokemonViewModel::class.java)
        viewModel.pokemons.observe(this, Observer {
            initRecyclerView(it)
        })
    }

    private fun initRecyclerView(pokemons: List<Pokemon?>) {
        binding.pokemonListRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.pokemonListRecyclerview.adapter = PokemonListAdapter(pokemons)
    }
}