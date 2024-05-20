package com.example.pokedex.model.response.request

import com.example.pokedex.model.response.domain.Pokemon
import com.example.pokedex.model.response.domain.Statistics
import com.example.pokedex.util.Utils


data class PokemonResponse (
    val id: Int,
    val name: String,
    val url: String,
    val sprites: SpritesResponse,
    val stats: List<Stats>,
    val abilities: List<Ability>,
    val types: List<Types>,
    val weight: Double,
    val height: Double
)

data class SpritesResponse (
    val front_default: String
)

data class Stats (
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
)

data class Stat (
    val name: String
)

data class Ability (
    val name: String
)

data class Types (
    val type: Type
)
data class Type (
    val name: String
)

fun PokemonResponse.toPokemon() = Pokemon(
    id,
    Utils.transformName(name),
    sprites.front_default,
    types.map { Utils.transformName(it.type.name) },
    weight,
    height,
    stats.map { Statistics(it.stat.name.uppercase(), it.base_stat) }
)