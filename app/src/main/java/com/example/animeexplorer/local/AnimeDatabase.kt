package com.example.animeexplorer.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animeexplorer.data.remote.detailCache.AnimeDetailDao
import com.example.animeexplorer.data.remote.detailCache.AnimeDetailEntity

@Database(entities = [AnimeEntity::class, AnimeDetailEntity::class], version = 2)
abstract class AnimeDatabase: RoomDatabase() {

    abstract fun animeDao(): AnimeDao
    abstract fun animeDetailDao(): AnimeDetailDao

    companion object{
        fun create(context: Context): AnimeDatabase =
            Room.databaseBuilder(
                context,
                AnimeDatabase::class.java,
                "anime_db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}