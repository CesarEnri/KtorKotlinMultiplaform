package org.cesarenri.ktorbasic.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.cesarenri.ktorbasic.data.movies
import org.cesarenri.ktorbasic.screens.ScreenCustom

class DetailScreen:Screen {

    @Composable
    override fun Content() {
            val movie = movies[0]
    ScreenCustom {
        Text(text = movie.title)
    }
    }
}
