package com.example.pokedex.model

import com.example.pokedex.model.PokemonRepository
//import com.example.pokedex.model.toPokemon

class GetPokemon(private val pokemonRepository: PokemonRepository) {
    private fun getPokemon(number: Int) {
        val pokemonApiResult = pokemonRepository.getPokemon(number)
//        pokemonApiResult?.toPokemon()
    }
}