package com.example.pokedex.data.model

import com.example.pokedex.domain.model.Pokemons

data class PokemonsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>
)

fun PokemonsResponse.toPokemons() = Pokemons(
    count,
    next,
    previous,
    results = results.map{
        it.toPokemon()
    }
)