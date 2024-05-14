package com.example.pokedex.model


data class PokemonResponse (
    val id: Int,
    val name: String,
    val url: String,
    val sprites: SpritesResponse,
)

data class SpritesResponse (
    val front_default: String
)

//fun PokemonResponse.toPokemon() = Pokemon(
//    id,
//    name,
//    image = sprites.front_default
//)