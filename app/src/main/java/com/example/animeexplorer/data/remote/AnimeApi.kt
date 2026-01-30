package com.example.animeexplorer.data.remote

import com.example.animeexplorer.data.remote.model.AnimeDetailDto
import com.example.animeexplorer.data.remote.model.AnimeDto
import com.example.animeexplorer.data.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {

    @GET("v4/top/anime")
    suspend fun getTopAnime(): ApiResponse<List<AnimeDto>>

    @GET("v4/anime/{id}")
    suspend fun getAnimeDetail(
        @Path("id") id: Int
    ): ApiResponse<AnimeDetailDto>
}