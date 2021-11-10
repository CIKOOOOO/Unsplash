package com.unsplash.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val IN_QUALIFIER = "in:name,description"
const val CLIENT_ID = "SUSLoy_msUHkDJoslVxAu3RHW09VTfWPi92ROdsO0rE"

interface UnsplashService {
    @GET("search/photos?client_id=${CLIENT_ID}&query=office")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): SplashSearchResponse

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): UnsplashService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashService::class.java)
        }
    }

}