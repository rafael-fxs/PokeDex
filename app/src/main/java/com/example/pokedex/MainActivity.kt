package com.example.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.presenter.PokemonViewModel
import javax.inject.Inject
import androidx.activity.viewModels
import com.example.pokedex.domain.GetPokemons

class MainActivity : AppCompatActivity() {
    private val viewModel: PokemonViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        System.out.println("teste2")
    }
}