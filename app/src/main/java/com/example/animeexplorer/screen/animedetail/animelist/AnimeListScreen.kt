package com.example.animeexplorer.screen.animedetail.animelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.animeexplorer.data.remote.ApiClient
import com.example.animeexplorer.local.AnimeDatabase
import com.example.animeexplorer.local.AnimeEntity
import com.example.animeexplorer.data.repository.AnimeRepository
import com.example.animeexplorer.utils.Resource

@Composable
fun AnimeListScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AnimeDatabase.create(context) }
    val viewModel = remember {
        AnimeListViewModel(
            AnimeRepository(ApiClient.api, db.animeDao(),db.animeDetailDao() ),
            context
        )
    }

    val state by viewModel.animeList.collectAsState()

    when (state) {
        is Resource.Loading -> CircularProgressIndicator()
        is Resource.Error -> Text("Error loading anime")
        is Resource.Success -> {
            LazyColumn {
                items((state as Resource.Success<List<AnimeEntity>>).data) { anime ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("detail/${anime.id}") }
                            .padding(8.dp)
                    ) {

                        Image(
                            painter = rememberAsyncImagePainter(
                                model = anime.imageUrl?:"",
                                placeholder = painterResource(android.R.drawable.ic_menu_report_image),
                                error = painterResource(android.R.drawable.ic_menu_report_image)
                            ),
                            contentDescription = anime.title,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = anime.title,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
