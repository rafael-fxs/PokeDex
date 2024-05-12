package com.example.pokedex.data

import com.example.pokedex.data.api.PokemonApi
import com.example.pokedex.data.model.PokemonResponse
import com.example.pokedex.data.model.PokemonsResponse
import com.example.pokedex.util.Constants.BASE_URL
import com.example.pokedex.util.Constants.PAGE_SIZE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(private var service: PokemonApi) : PokemonRepository {
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(service::class.java)
    }

    override fun listPokemons(): PokemonsResponse? {
        val call = service.listPokemons(PAGE_SIZE)
        return call.execute().body()
    }

    override fun getPokemon(number: Int): PokemonResponse? {
        val call = service.getPokemon(number)
        return call.execute().body()
    }
}

interface PokemonRepository {
    fun listPokemons(): PokemonsResponse?
    fun getPokemon(number: Int): PokemonResponse?
}