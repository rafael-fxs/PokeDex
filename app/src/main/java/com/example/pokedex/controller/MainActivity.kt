package com.example.pokedex.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R
import com.example.pokedex.model.Singleton
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: PokemonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Singleton.setContext(this)
        viewModel = PokemonViewModelFactory().create(PokemonViewModel::class.java)
        viewModel.pokemons.observe(this){
            var teste = this;
            System.out.println("Teste")
        }
        viewModel.refresh()
        viewModel.pokemons

        System.out.println("Teste")
    }
}