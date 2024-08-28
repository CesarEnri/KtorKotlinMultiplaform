package org.cesarenri.ktorbasic.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.cesarenri.ktorbasic.data.Movie
import org.cesarenri.ktorbasic.data.movies


class MovieScreen: Screen {

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    override fun Content() {
        setSingletonImageLoaderFactory { context ->  ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build() }
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = { TopAppBar(title = { Text("Movies") }) }
            ) {
                paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(paddingValues))
                {
                    items(movies, key = { it.id})
                    {
                        MovieItem(movie = it)
                    }
                }
            }

        }
    }

    @Composable
    private fun MovieItem(movie: Movie) {
        Column {
            KamelImage(
                resource = asyncPainterResource(data = movie.poster),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray),
            )


            Text(text = movie.title,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp))
            }
        }
}