package com.example.pokedex.domain

import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.model.toPokemon
import com.example.pokedex.domain.model.Pokemon

class GetPokemon(private val pokemonRepository: PokemonRepository) {
    private fun getPokemon(number: Int) {
        val pokemonApiResult = pokemonRepository.getPokemon(number)
        pokemonApiResult?.toPokemon()
    }
}

interface GetPokemonUseCase {
    suspend operator fun invoke(number: Int): Pokemon
}
