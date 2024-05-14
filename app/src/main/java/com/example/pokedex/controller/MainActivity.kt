package com.example.pokedex.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.viewmodel.PokemonViewModelFactory
import kotlinx.coroutines.launch

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
        viewModel.viewModelScope.launch {
            viewModel.pokemons.observe(this@MainActivity, Observer {
                initRecyclerView(it)
            })
        }
    }

    private fun initRecyclerView(pokemons: List<Pokemon?>) {
        binding.pokemonListRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.pokemonListRecyclerview.adapter = PokemonListAdapter(pokemons, object : PokemonListAdapter.OnCityClickListener {
            override fun onPokemonClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }
}