package com.example.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.PokemonsResponse
import com.example.pokedex.model.Singleton

class PokemonViewModel : ViewModel() {

    var pokemons = MutableLiveData<PokemonsResponse>()

    fun refresh(){
        pokemons.value = Singleton.pokemons.value
    }
}