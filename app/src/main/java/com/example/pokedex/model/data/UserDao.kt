package com.example.pokedex.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)
    @Query("Select * from user")
    fun getAll(): List<UserEntity>
    @Query("Select * from user where username = :username")
    fun getByUsername(username: String): UserEntity
    @Query("Select * from user where username = :username and password = :password")
    fun getByUsernameAndPassword(username: String, password: String): UserEntity
    @Update
    fun update(user: UserEntity): Int
    @Delete
    fun delete(user: UserEntity): Int
}