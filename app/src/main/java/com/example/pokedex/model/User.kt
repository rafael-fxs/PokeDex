package com.example.pokedex.model

import com.google.gson.Gson
import java.io.Serializable

class User (var username: String, var password: String) : Serializable {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): User {
            return Gson().fromJson(json, User::class.java)
        }
    }
}
