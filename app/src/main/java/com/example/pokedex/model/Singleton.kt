package com.example.pokedex.model

import com.example.pokedex.model.data.UserEntity

object Singleton {
    private var currentUser: UserEntity? = null

    fun setCurrentUser(user: UserEntity){
        currentUser = user;
    }

    fun getCurrentUser(): UserEntity? {
        return currentUser
    }
}