package com.example.pokedex.domain

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.model.toPokemon
import com.example.pokedex.data.model.toPokemons
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.Pokemons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetPokemons @Inject constructor(private val pokemonRepository: PokemonRepository) : GetPokemonsUseCase {
    private var pokemons = MutableLiveData<List<Pokemon?>>()

    override suspend fun invoke(): Pokemons? = try {
        val pokemonsApiResult = pokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let {
            pokemons.postValue(it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = pokemonRepository.getPokemon(number)
                pokemonApiResult?.toPokemon()
            })
        }
        pokemonsApiResult?.toPokemons()
    } catch (ex: Exception) {
        Pokemons(0, "", "", listOf())
    }
}

interface GetPokemonsUseCase {
    suspend operator fun invoke(): Pokemons?
}