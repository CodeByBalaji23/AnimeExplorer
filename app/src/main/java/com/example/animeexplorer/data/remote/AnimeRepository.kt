package com.example.animeexplorer.data.repository

import android.content.Context
import com.example.animeexplorer.data.remote.AnimeApi
import com.example.animeexplorer.data.remote.detailCache.AnimeDetailDao
import com.example.animeexplorer.data.remote.detailCache.AnimeDetailEntity
import com.example.animeexplorer.local.AnimeDao
import com.example.animeexplorer.local.AnimeEntity
import com.example.animeexplorer.data.remote.model.AnimeDetailDto
import com.example.animeexplorer.data.remote.model.Images
import com.example.animeexplorer.data.remote.model.Jpg
import com.example.animeexplorer.utils.NetworkUtils
import com.example.animeexplorer.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AnimeRepository(
    private val api: AnimeApi,
    private val animeDao: AnimeDao,
    private val detailDao: AnimeDetailDao
) {

    fun getAnimeList(context: Context): Flow<Resource<List<AnimeEntity>>> = flow {
        emit(Resource.Loading())

        val cached = animeDao.getAllAnime().first()
        if (cached.isNotEmpty()) {
            emit(Resource.Success(cached))
        }

        if (NetworkUtils.isNetworkAvailable(context)) {
            try {
                val response = api.getTopAnime()
                val entities = response.data.map {
                    AnimeEntity(
                        id = it.malId,
                        title = it.title,
                        episodes = it.episodes,
                        rating = it.score,
                        imageUrl = it.images.jpg.imageUrl
                    )
                }
                animeDao.insertAll(entities)
                emit(Resource.Success(entities))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch remote, showing cached data"))
                emit(Resource.Success(cached))
            }
        } else {
            if (cached.isNotEmpty()) {
                emit(Resource.Success(cached))
            } else {
                emit(Resource.Error("No internet connection and no cached data"))
            }
        }
    }

    suspend fun getAnimeDetail(id: Int, context: Context): Resource<AnimeDetailDto> {
        val cached = detailDao.getDetail(id)

        if (!NetworkUtils.isNetworkAvailable(context)) {
            return if (cached != null) {
                Resource.Success(
                    AnimeDetailDto(
                        malId = cached.id,
                        title = cached.title,
                        episodes = cached.episodes,
                        score = cached.rating,
                        images = Images(Jpg(cached.imageUrl))
                    )
                )
            } else {
                Resource.Error("No internet connection")
            }
        }

        return try {
            val detail = api.getAnimeDetail(id).data
            detailDao.insertDetail(
                AnimeDetailEntity(
                    id = detail.malId,
                    title = detail.title,
                    episodes = detail.episodes,
                    rating = detail.score,
                    imageUrl = detail.images.jpg.imageUrl
                )
            )
            Resource.Success(detail)
        } catch (e: Exception) {
            if (cached != null) {
                Resource.Success(
                    AnimeDetailDto(
                        malId = cached.id,
                        title = cached.title,
                        episodes = cached.episodes,
                        score = cached.rating,
                        images = Images(Jpg(cached.imageUrl))
                    )
                )
            } else {
                Resource.Error("Failed to load details")
            }
        }
    }
}
