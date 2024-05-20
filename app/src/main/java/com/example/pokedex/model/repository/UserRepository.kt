package com.example.pokedex.model.repository

import android.content.Context
import com.example.pokedex.model.data.UserDao
import com.example.pokedex.model.data.UserDatabase
import com.example.pokedex.model.data.UserEntity

object UserRepository {
    private lateinit var dao: UserDao
    fun setContext(context: Context){
        UserDatabase.getInstance(context)?.apply {
            dao = userDao()
        }
    }

    fun add(user: UserEntity){
        dao.insert(user)
    }

    fun getByUsername(username: String): UserEntity? {
        return dao.getByUsername(username)
    }

    fun getByUsernameAndPassword(username: String, password: String): UserEntity? {
        return dao.getByUsernameAndPassword(username, password)
    }
    fun update(user: UserEntity){
        dao.update(user)
    }
    fun delete(user: UserEntity){
        dao.delete(user)
    }
}
