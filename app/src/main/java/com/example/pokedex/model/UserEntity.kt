package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.io.Serializable

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var username: String,
    var password: String
) : Serializable {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): UserEntity {
            return Gson().fromJson(json, UserEntity::class.java)
        }
    }
}
