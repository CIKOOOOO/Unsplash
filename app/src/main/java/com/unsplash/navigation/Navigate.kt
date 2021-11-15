package com.unsplash.navigation

import com.unsplash.model.Unsplash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

interface Navigate {

    fun navigate(unsplash: Unsplash)
}