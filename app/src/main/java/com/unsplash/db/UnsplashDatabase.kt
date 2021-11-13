package com.unsplash.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unsplash.model.Unsplash

@Database(
    entities = [Unsplash::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}