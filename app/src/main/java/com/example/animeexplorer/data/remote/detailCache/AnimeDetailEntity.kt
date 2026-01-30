package com.example.animeexplorer.data.remote.detailCache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_detail")
data class AnimeDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val episodes: Int?,
    val rating: Double?,
    val imageUrl: String?
)
