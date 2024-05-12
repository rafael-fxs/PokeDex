package com.example.pokedex.data.model

import com.example.pokedex.domain.model.Pokemon


data class PokemonResponse (
    val id: Int,
    val name: String,
    val url: String,
    val sprites: SpritesResponse,
)

data class SpritesResponse (
    val front_default: String
)

fun PokemonResponse.toPokemon() = Pokemon(
    id,
    name,
    image = sprites.front_default
)