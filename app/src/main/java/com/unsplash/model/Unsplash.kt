package com.unsplash.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "unsplash")
data class Unsplash(
    @PrimaryKey val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("picture") val picture: String,
    @field:SerializedName("description") val description: String = ""
)