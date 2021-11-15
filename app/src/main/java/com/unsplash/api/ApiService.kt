package com.unsplash.api

import com.unsplash.di.NetworkModule.CLIENT_ID
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: Client-ID v4jsKEPplqfF6yUQSk84j0eY4Bn6mpnbiZlpXhXQ1h0")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): SplashSearchResponse

    @Headers("X-API-KEY: 454041184B0240FBA3AACD15A1F7A8BB")
    @POST()
    @FormUrlEncoded
    suspend fun loginService(@Url string: String, @FieldMap param: Map<String,String>) : Response<LoginResponse>
}