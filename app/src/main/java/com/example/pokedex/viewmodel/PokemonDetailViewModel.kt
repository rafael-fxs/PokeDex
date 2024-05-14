package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonDetailViewModel() : ViewModel() {

    suspend fun getPokemonInfo(id: Int): Pokemon? {
        return withContext(Dispatchers.IO) {
            val pokemonApiResult = PokemonRepository.getPokemon(id)
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