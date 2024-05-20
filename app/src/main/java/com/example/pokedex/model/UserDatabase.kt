package com.example.pokedex.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.util.Converters

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase?{
            if (instance == null){
                synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user.sqlite"
                            )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }
}