package com.example.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()
    var loading = MutableLiveData<Boolean>()

    init {
        loading.value = true // Inicialmente, está carregando
        viewModelScope.launch {
            loadPokemons()
        }
    }

    private suspend fun loadPokemons() {
        val pokemonsApiResult = withContext(Dispatchers.IO) {
            loading.postValue(true) // Indicar que está carregando
            PokemonRepository.listPokemons()
        }

        pokemonsApiResult?.results?.let {
            val pokemonList = it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = withContext(Dispatchers.IO) {
                    PokemonRepository.getPokemon(number)
                }

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.sprites.front_default
                    )
                }
            }
            pokemons.postValue(pokemonList)
            loading.postValue(false)
        }
    }
}