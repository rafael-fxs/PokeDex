package com.example.pokedex.model.response.request

data class PokemonsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>
)