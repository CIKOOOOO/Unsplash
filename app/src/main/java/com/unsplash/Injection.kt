package com.unsplash

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.unsplash.api.UnsplashService
import com.unsplash.data.UnsplashRepository
import com.unsplash.db.UnsplashDatabase
import com.unsplash.ui.ViewModelFactory

object Injection {

    private fun provideUnsplashRepository(context: Context): UnsplashRepository {
        return UnsplashRepository(UnsplashService.create(), UnsplashDatabase.getInstance(context))
    }

    fun provideViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideUnsplashRepository(context))
    }

}