package com.unsplash.api

import com.unsplash.di.NetworkModule.CLIENT_ID
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/photos?client_id=${CLIENT_ID}&query=office")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): SplashSearchResponse
}