package com.example.animeexplorer.screen.animedetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeexplorer.data.remote.model.AnimeDetailDto
import com.example.animeexplorer.data.repository.AnimeRepository
import com.example.animeexplorer.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AnimeDetailViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<AnimeDetailDto>>(Resource.Loading())
    val state: StateFlow<Resource<AnimeDetailDto>> = _state

    fun load(id: Int, context: Context) {
        viewModelScope.launch {
            _state.value = repository.getAnimeDetail(id, context)
        }

    }
}