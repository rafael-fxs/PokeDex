package com.example.pokedex.model
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String,
    val types: List<String>,
    val weight: Double,
    val height: Double,
    val stats: List<Statistics>,
) {
    val typeColor: String
        get() = getTypeColor(types[0].lowercase())

    companion object {
        fun getTypeColor(type: String): String {
            return when (type) {
                "normal" -> "#A8A878"
                "fire" -> "#F08030"
                "water" -> "#6890F0"
                "electric" -> "#F8D030"
                "grass" -> "#78C850"
                "ice" -> "#98D8D8"
                "fighting" -> "#C03028"
                "poison" -> "#A040A0"
                "ground" -> "#E0C068"
                "flying" -> "#A890F0"
                "psychic" -> "#F85888"
                "bug" -> "#A8B820"
                "rock" -> "#B8A038"
                "ghost" -> "#705898"
                "dragon" -> "#7038F8"
                "dark" -> "#705848"
                "steel" -> "#B8B8D0"
                "fairy" -> "#EE99AC"
                else -> "#FFFFFF" // Default color if type is not found
            }
        }
    }
}

data class Statistics (
    val name: String,
    val value: Int,
) {
    val color: String
        get() = getStatsColor(name.lowercase())

    companion object {
        fun getStatsColor(name: String): String {
            return when (name) {
                "hp" -> "#f54336"
                "attack" -> "#47a6ff"
                "defence" -> "#ff94d1"
                "special-attack" -> "#9bfc72"
                "special-defense" -> "#9f82ff"
                "speed" -> "#ffe482"
                else -> "#FFCC33"
            }
        }
    }
}

