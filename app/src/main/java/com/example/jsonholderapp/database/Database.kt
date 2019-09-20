package com.example.jsonholderapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jsonholderapp.models.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun postDao(): PostDao
}