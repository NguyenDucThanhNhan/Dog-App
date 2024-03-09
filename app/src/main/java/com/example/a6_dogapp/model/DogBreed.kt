package com.example.a6_dogapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DogBreed(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("url")
    val url: String
): Serializable
