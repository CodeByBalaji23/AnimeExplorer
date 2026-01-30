package com.example.animeexplorer.data.remote.detailCache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimeDetailDao {
    @Query("SELECT * FROM anime_detail WHERE id = :id")
    suspend fun getDetail(id: Int): AnimeDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(detail: AnimeDetailEntity)
}
