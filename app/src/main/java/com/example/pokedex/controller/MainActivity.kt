package com.example.pokedex.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.viewmodel.PokemonViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private var filteredPokemons: List<Pokemon?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        initViewModel()
        setupSearchView()
        observeLoadingState()
    }

    private fun initViewModel() {
        viewModel = PokemonViewModelFactory().create(PokemonViewModel::class.java)
        viewModel.pokemons.observe(this, Observer {
            filteredPokemons = it
            updateRecyclerView()
        })
    }

    private fun setupSearchView() {
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterPokemons(newText)
                return true
            }
        })
    }

    private fun filterPokemons(query: String?) {
        if (query.isNullOrBlank()) {
            filteredPokemons = viewModel.pokemons.value ?: listOf()
        } else {
            val filteredList = viewModel.pokemons.value?.filter { pokemon ->
                pokemon?.name?.contains(query, ignoreCase = true) ?: false
            }
            filteredPokemons = filteredList ?: listOf()
        }

        if (filteredPokemons.isNullOrEmpty() && !query.isNullOrBlank()) {
            searchPokemon(query)
        } else {
            updateRecyclerView()
        }
    }

    private fun searchPokemon(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val pokemon = viewModel.getPokemonInfo(query)
            pokemon?.let {
                filteredPokemons = listOf(it)
                updateRecyclerView()
            }
        }
    }

    private fun observeLoadingState() {
        viewModel.loading.observe(this) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.searchView.isEnabled = !isLoading
        }
    }

    private fun updateRecyclerView() {
        binding.pokemonListRecyclerview.layoutManager = GridLayoutManager(this, 2)
        binding.pokemonListRecyclerview.adapter = PokemonListAdapter(filteredPokemons, object : PokemonListAdapter.OnCityClickListener {
            override fun onPokemonClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, PokemonDetailActivity::class.java)
                filteredPokemons[position]?.let { intent.putExtra("pokemon_id", it.id) }
                startActivity(intent)
            }
        })
    }
}