package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonsFavoriteViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonsFavoriteViewModel::class.java)){
            PokemonsFavoriteViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}