package com.example.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonRepository
import com.example.pokedex.model.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()
    var loading = MutableLiveData<Boolean>()

    init {
        loading.value = true
        viewModelScope.launch {
            loadPokemons()
        }
    }

    private suspend fun loadPokemons() {
        val pokemonsApiResult = withContext(Dispatchers.IO) {
            loading.postValue(true)
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

                pokemonApiResult?.toPokemon()
            }
            pokemons.postValue(pokemonList)
            loading.postValue(false)
        }
    }

    suspend fun getPokemonInfo(name: String): Pokemon? {
        loading.postValue(true)
        return withContext(Dispatchers.IO) {
            val pokemonApiResult = PokemonRepository.getPokemon(name)
            loading.postValue(false) // Indica que parou de carregar

            pokemonApiResult?.toPokemon()
        }
    }
}