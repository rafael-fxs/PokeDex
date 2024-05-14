package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)){
            PokemonDetailViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}