package com.example.pokedex.viewmodel.registeruser

import androidx.lifecycle.ViewModel
import com.example.pokedex.model.data.UserEntity
import com.example.pokedex.model.repository.UserRepository

class RegisterUserViewModel : ViewModel() {

    fun getByUsername(username: String): UserEntity? {
        return UserRepository.getByUsername(username)
    }

    fun createUser(user: UserEntity) {
        return UserRepository.add(user)
    }
}