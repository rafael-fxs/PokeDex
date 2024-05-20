package com.example.pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonRepository
import com.example.pokedex.model.PokemonResponse
import com.example.pokedex.model.Singleton
import com.example.pokedex.model.UserRepository
import com.example.pokedex.model.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel : ViewModel() {
    var loading = MutableLiveData<Boolean>()
    var pokemonDetail = MutableLiveData<Pokemon?>()
    var isFavorite = MutableLiveData<Boolean>()

    init {
        loading.value = true
        isFavorite.value = false
    }

    suspend fun getPokemonInfo(id: Int): Pokemon? {
        loading.postValue(true)
        return withContext(Dispatchers.IO) {
            val pokemonApiResult = PokemonRepository.getPokemon(id)
            loading.postValue(false)

            pokemonApiResult?.toPokemon().apply {
                pokemonDetail.postValue(this)
                this?.let { checkIfFavorite(it) }
            }
        }
    }

    private fun checkIfFavorite(pokemon: Pokemon) {
        val currentUser = Singleton.getCurrentUser()
        currentUser?.let {
            isFavorite.postValue(it.favoritePokemons.contains(pokemon.id))
        }
    }

    fun toggleFavorite() {
        val user = Singleton.getCurrentUser()
        val pokemon = pokemonDetail.value ?: return

        viewModelScope.launch {
            if (user != null) {
                if (user.favoritePokemons.contains(pokemon.id)) {
                    user.favoritePokemons.remove(pokemon.id)
                    isFavorite.postValue(false)
                } else {
                    user.favoritePokemons.add(pokemon.id)
                    isFavorite.postValue(true)
                }
                UserRepository.update(user)
            }
        }
    }
}
