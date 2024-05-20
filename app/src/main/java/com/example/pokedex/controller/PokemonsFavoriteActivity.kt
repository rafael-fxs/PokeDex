package com.example.pokedex.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonsFavoriteBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.viewmodel.PokemonsFavoriteViewModel
import com.example.pokedex.viewmodel.PokemonsFavoriteViewModelFactory

class PokemonsFavoriteActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonsFavoriteViewModel
    private lateinit var binding: ActivityPokemonsFavoriteBinding
    private lateinit var searchView: SearchView
    private var filteredPokemons: List<Pokemon?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_pokemons_favorite
        )
        initViewModel()
        setupSearchView()
        observeLoadingState()
    }

    private fun initViewModel() {
        viewModel = PokemonsFavoriteViewModelFactory().create(PokemonsFavoriteViewModel::class.java)
        viewModel.pokemons.observe(this) {
            filteredPokemons = it
            updateRecyclerView()
        }
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
        updateRecyclerView()
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
                val intent = Intent(this@PokemonsFavoriteActivity, PokemonDetailActivity::class.java)
                filteredPokemons[position]?.let { intent.putExtra("pokemon_id", it.id) }
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }
}