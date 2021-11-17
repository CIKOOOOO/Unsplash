package com.unsplash.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UnsplashData(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val url: Url,
    @SerializedName("user") val user: User,
    @SerializedName("alt_description") val description: String = "",
    @SerializedName("likes") val likes: Int,
    @SerializedName("liked_by_user") val likeByUser: Boolean
)

@Keep
data class Url(
    @SerializedName("regular") val picture: String
)

@Keep
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_image") val profileImage: ProfileImage,
    @SerializedName("twitter_username") val twitterUsername: String,
    @SerializedName("total_photos") val totalPhotos: Int
)

@Keep
data class ProfileImage(
    @SerializedName("medium") val medium: String
)