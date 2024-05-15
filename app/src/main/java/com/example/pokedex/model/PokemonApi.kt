package com.example.pokedex.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsResponse>

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Call<PokemonResponse>
}