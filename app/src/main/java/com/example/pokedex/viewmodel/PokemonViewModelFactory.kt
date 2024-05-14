package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonViewModel::class.java)){
            PokemonViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}