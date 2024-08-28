package org.cesarenri.ktorbasic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
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
import org.cesarenri.ktorbasic.bottombar.BottomBarScreen
import org.cesarenri.ktorbasic.settings.MovieScreen
import org.cesarenri.ktorbasic.settings.ProfileScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = MainScreen())
        {
            navigator ->
            SlideTransition(navigator)
            //FadeTransition(navigator)
            //ScaleTransition(navigator)
        }
    }
}

class MainScreen:Screen
{
    @Composable
    override fun Content() {
        val response = GetDeviceInformation().getDeviceInfo()
        val navigator = LocalNavigator.currentOrThrow

        var name: String by remember { mutableStateOf("") }
        var superheroName by remember { mutableStateOf("") }
        var superHeroList by remember { mutableStateOf<List<Hero>>(emptyList()) }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier =Modifier.height(8.dp))
            TextField(value = name, onValueChange = {name = it})

            Spacer(modifier = Modifier.height(38.dp))
            AnimatedVisibility(name.isNotEmpty())
            {
                Text("Hello $name  plataform $response", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(38.dp))

            Row { TextField(value = superheroName, onValueChange = { superheroName = it})
                Button(onClick = {getSuperHeroList(superheroName){superHeroList = it} })
                {
                    Text("Load")
                }
            }

            Button(onClick = {
                navigator.push(SecondScreen())
            })
            {
                Text("Navegacion basica")
            }

            Spacer(modifier = Modifier.height(18.dp))
            Button(onClick = {
                navigator.push(BottomBarScreen())
            })
            {
                Text("BottomBar")
            }

            Spacer(modifier = Modifier.height(38.dp))
            Button(onClick = {
                navigator.push(ProfileScreen())
            })
            {
                Text("Persistencia")
            }

            Spacer(modifier = Modifier.height(38.dp))
            Button(onClick = {
                navigator.push(MovieScreen())
            })
            {
                Text("Peliculas")
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

class SecondScreen:Screen
{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.fillMaxSize().background(Color.Blue)
        , horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("Segunda pantalla", fontSize = 26.sp, color = Color.White)
            Button(onClick = {
                navigator.pop()
            })
            {
                Text("Volver")
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
