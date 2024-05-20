package com.example.pokedex.model

object Singleton {
    private var currentUser: UserEntity? = null

    fun setCurrentUser(user: UserEntity){
        currentUser = user;
    }

    fun getCurrentUser(): UserEntity? {
        return currentUser
    }
}