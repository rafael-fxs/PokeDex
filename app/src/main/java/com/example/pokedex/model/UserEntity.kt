package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pokedex.util.Converters
import com.google.gson.Gson
import java.io.Serializable

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val username: String,
    val password: String,
    @TypeConverters(Converters::class)
    val favoritePokemons: MutableList<Int> = mutableListOf()

) : Serializable
