package com.example.pokedex.viewmodel.pokemonsfavorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Singleton
import com.example.pokedex.model.repository.PokemonRepository
import com.example.pokedex.model.response.domain.Pokemon
import com.example.pokedex.model.response.request.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonsFavoriteViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()
    var loading = MutableLiveData<Boolean>()

    init {
        loading.value = true
        viewModelScope.launch {
            loadPokemons()
        }
    }

    private suspend fun loadPokemons() {
        val favoriteListPokemonsId: List<Int> = Singleton.getCurrentUser()?.favoritePokemons ?: listOf()
        return withContext(Dispatchers.IO) {
            val pokemonFavoriteList = mutableListOf<Pokemon>()
            for (id in favoriteListPokemonsId) {
                PokemonRepository.getPokemon(id)?.toPokemon().apply {
                    this?.let { pokemonFavoriteList.add(it) }
                }
            }
            pokemons.postValue(pokemonFavoriteList)
            loading.postValue(false)
        }
    }
}