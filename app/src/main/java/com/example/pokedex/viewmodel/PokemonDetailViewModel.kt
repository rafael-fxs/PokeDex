package com.example.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonDetailViewModel() : ViewModel() {
    var loading = MutableLiveData<Boolean>()

    init {
        loading.value = true
    }

    suspend fun getPokemonInfo(id: Int): Pokemon? {
        loading.postValue(true)
        return withContext(Dispatchers.IO) {
            val pokemonApiResult = PokemonRepository.getPokemon(id)
            loading.postValue(false)

            pokemonApiResult?.let {
                Pokemon(
                    pokemonApiResult.id,
                    pokemonApiResult.name,
                    pokemonApiResult.sprites.front_default
                )
            }
        }
    }
}
