package com.unsplash.di

import android.app.Application
import com.unsplash.utils.SharedPreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun providesPreferences(application: Application): SharedPreferenceUtil {
        return SharedPreferenceUtil(application)
    }
}