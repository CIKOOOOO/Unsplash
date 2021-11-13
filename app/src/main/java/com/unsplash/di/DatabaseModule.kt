package com.unsplash.di

import android.content.Context
import androidx.room.Room
import com.unsplash.db.UnsplashDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        UnsplashDatabase::class.java,
        "app_db"
    ).build()

    @Singleton
    @Provides
    fun provideUnsplashDao(db: UnsplashDatabase) = db.unsplashDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(db: UnsplashDatabase) = db.remoteKeysDao()
}