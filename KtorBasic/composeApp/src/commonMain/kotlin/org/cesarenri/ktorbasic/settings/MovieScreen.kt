package org.cesarenri.ktorbasic.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ktorbasic.composeapp.generated.resources.Res
import ktorbasic.composeapp.generated.resources.app_name
import org.cesarenri.ktorbasic.data.Movie
import org.cesarenri.ktorbasic.data.movies
import org.cesarenri.ktorbasic.screens.ScreenCustom
import org.jetbrains.compose.resources.stringResource

class MovieScreen: Screen {


    @OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        setSingletonImageLoaderFactory { context ->  ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build() }
        HomeScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomeScreen() {
        ScreenCustom()
        {
            val scrollBehaviourData = TopAppBarDefaults.pinnedScrollBehavior()
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(Res.string.app_name)) },
                        scrollBehavior = scrollBehaviourData
                    )
                }, modifier = Modifier.nestedScroll(scrollBehaviourData.nestedScrollConnection)
            ) { paddingValues ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(paddingValues)
                )
                {
                    items(movies, key = { it.id })
                    {
                        MovieItem(movie = it)
                    }
                }
            }

        }
    }

    @Composable
    private fun MovieItem(movie: Movie) {
        val navigator = LocalNavigator.currentOrThrow

        Button( onClick =
        {
            navigator.push(DetailScreen())
        })
        {
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
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp))
            }
        }
        }

}