package com.example.pokedex.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.GetPokemonsUseCase
import com.example.pokedex.domain.model.Pokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel
    @Inject constructor(private val getPokemonsUseCase: GetPokemonsUseCase)
    : ViewModel() {

    private val _pokemons = MutableLiveData<Pokemons>()

    fun getPokemons() {
        viewModelScope.launch {
            _pokemons.value = getPokemonsUseCase()
        }
    }
}