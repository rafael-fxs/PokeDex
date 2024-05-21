package com.example.pokedex.viewmodel.pokemonhome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.repository.PokemonRepository
import com.example.pokedex.model.response.domain.Pokemon
import com.example.pokedex.model.response.request.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonHomeViewModel : ViewModel() {
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
                val pokemonApiResult = withContext(Dispatchers.IO) {
                    PokemonRepository.getPokemon(pokemonResult.name)
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