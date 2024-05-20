package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PokemonHomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonHomeViewModel::class.java)){
            PokemonHomeViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}