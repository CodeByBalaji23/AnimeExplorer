package com.example.animeexplorer.screen.animedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.animeexplorer.data.remote.ApiClient
import com.example.animeexplorer.local.AnimeDatabase
import com.example.animeexplorer.data.repository.AnimeRepository
import com.example.animeexplorer.utils.Resource

@Composable
fun AnimeDetailScreen(animeId: Int) {
    val context = LocalContext.current
    val db = remember { AnimeDatabase.create(context) }
    val viewModel = remember {
        AnimeDetailViewModel(
            AnimeRepository(ApiClient.api, db.animeDao(),db.animeDetailDao() )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.load(animeId, context)
    }

    val state by viewModel.state.collectAsState()

    when (val result = state) {
        is Resource.Loading -> CircularProgressIndicator()
        is Resource.Error -> Text("Error loading details")
        is Resource.Success -> {
            val anime = result.data
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = anime.imageUrl ?: "",
                        placeholder = painterResource(android.R.drawable.ic_menu_report_image),
                        error = painterResource(android.R.drawable.ic_menu_report_image)
                    ),
                    contentDescription = anime.title,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = anime.title,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = "Episodes: ${anime.episodes ?: "N/A"}")
                Text(text = "Rating: ${anime.rating ?: "N/A"}")
            }
        }
    }
}
