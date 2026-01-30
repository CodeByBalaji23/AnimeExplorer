package com.example.animeexplorer.data.remote.model

import com.google.gson.annotations.SerializedName

data class AnimeDetailDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: Images
) {
    val rating: Double? get() = score
    val imageUrl: String? get() = images.jpg.imageUrl
}
