package com.example.pokedex.model

import com.example.pokedex.util.Constants.BASE_URL
import com.example.pokedex.util.Constants.PAGE_SIZE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val service: PokemonApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokemonApi::class.java)
    }

    fun listPokemons(): PokemonsResponse? {
        val call = service.listPokemons(PAGE_SIZE)
        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonResponse? {
        val call = service.getPokemon(number)
        return call.execute().body()
    }
}
