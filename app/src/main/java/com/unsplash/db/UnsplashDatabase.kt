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

    companion object {

        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getInstance(context: Context): UnsplashDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UnsplashDatabase::class.java, "Unsplash.db"
            ).build()

    }

}