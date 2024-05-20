package com.example.pokedex.viewmodel.login

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.data.UserEntity
import com.example.pokedex.model.repository.UserRepository

class LoginViewModel : ViewModel() {
    fun getByUsernameAndPassword(username: String, password: String): UserEntity? {
        return UserRepository.getByUsernameAndPassword(username, password)
    }
}