package org.cesarenri.ktorbasic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import network.NetworkUtils
import network.model.ApiResponse
import network.model.Hero
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var superheroName by remember { mutableStateOf("") }
        var superHeroList by remember { mutableStateOf<List<Hero>>(emptyList()) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row { TextField(value = superheroName, onValueChange = { superheroName = it})
                Button(onClick = {getSuperHeroList(superheroName){superHeroList = it} })
                {
                    Text("Load")
                }
             }
            //list
            LazyColumn {
                items(superHeroList){
                    hero -> Text(hero.name)
                }
            }

        }
    }
}

fun getSuperHeroList(superheroName: String, onSuccessResponse: (List<Hero>) -> Unit) {

    if (superheroName.isBlank())
        return
    val url = "https://www.superheroapi.com/api.php/6ec08c1c3893984a663d141a671811d1/search/$superheroName"

    CoroutineScope(Dispatchers.IO).launch {
        val response = NetworkUtils.httpClient.get(url).body<ApiResponse>()
        onSuccessResponse(response.results)
    }
}
