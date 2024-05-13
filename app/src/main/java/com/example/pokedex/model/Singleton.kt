package com.example.pokedex.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.util.Constants.PAGE_SIZE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Singleton {
    lateinit var pokemons: MutableLiveData<PokemonsResponse>

    fun setContext(context: Context){
        pokemons = MutableLiveData()
        PokemonRepository.getRetroInstance().apply {
            val service = this.create(PokemonApi::class.java)
            val call = service.listPokemons(PAGE_SIZE)
            call.enqueue(object : Callback<PokemonsResponse> {
                override fun onFailure(call: Call<PokemonsResponse>, t: Throwable) {
                    pokemons.postValue(null)
                }
                override fun onResponse(
                    call: Call<PokemonsResponse>,
                    response: Response<PokemonsResponse>
                ) {
                    pokemons.postValue(response.body())
                }
            })
        }
    }
}