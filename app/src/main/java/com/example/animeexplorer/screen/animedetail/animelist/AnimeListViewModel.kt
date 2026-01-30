package com.example.animeexplorer.screen.animedetail.animelist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeexplorer.data.repository.AnimeRepository
import com.example.animeexplorer.utils.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


class AnimeListViewModel(
     repository: AnimeRepository,
     context: Context
) : ViewModel() {

    val animeList = repository.getAnimeList(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading())
}