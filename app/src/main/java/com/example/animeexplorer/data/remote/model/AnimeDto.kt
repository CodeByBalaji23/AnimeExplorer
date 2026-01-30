package com.example.animeexplorer.data.remote.model

import com.google.gson.annotations.SerializedName


data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: Images
)

data class Images(
    val jpg: Jpg
)

data class Jpg(
    @SerializedName("image_url")
    val imageUrl: String?
)
