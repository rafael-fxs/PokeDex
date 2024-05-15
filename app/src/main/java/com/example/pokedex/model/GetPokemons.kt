package com.example.pokedex.model

//import com.example.pokedex.model.toPokemon
//import com.example.pokedex.model.toPokemons
import androidx.lifecycle.MutableLiveData

class GetPokemons {
    private var pokemons = MutableLiveData<List<Pokemon?>>()

//     fun getPokemons() = try {
//        val pokemonsApiResult = pokemonRepository.listPokemons()
//
//        pokemonsApiResult?.results?.let {
//            pokemons.postValue(it.map { pokemonResult ->
//                val number = pokemonResult.url
//                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
//                    .replace("/", "").toInt()
//
//                val pokemonApiResult = pokemonRepository.getPokemon(number)
//                pokemonApiResult?.toPokemon()
//            })
//        }
//        pokemonsApiResult?.toPokemons()
//    } catch (ex: Exception) {
//        Pokemons(0, "", "", listOf())
//    }
}
