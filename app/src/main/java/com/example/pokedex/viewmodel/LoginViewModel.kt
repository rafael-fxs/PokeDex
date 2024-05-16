package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UserEntity
import com.example.pokedex.model.UserRepository

class LoginViewModel : ViewModel() {
    fun getByUsernameAndPassword(username: String, password: String): UserEntity? {
        return UserRepository.getByUsernameAndPassword(username, password)
    }
}