package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.UserEntity
import com.example.pokedex.model.UserRepository

class RegisterUserViewModel : ViewModel() {

    fun getByUsername(username: String): UserEntity? {
        return UserRepository.getByUsername(username)
    }

    fun createUser(user: UserEntity) {
        return UserRepository.add(user)
    }
}