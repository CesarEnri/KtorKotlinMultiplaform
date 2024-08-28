package org.cesarenri.ktorbasic.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import org.cesarenri.ktorbasic.settings.ProfileScreen.Companion.KEY_NAME
import org.cesarenri.ktorbasic.settings.ProfileScreen.Companion.KEY_VIP



class ProfileResultScreen: Screen {

    private val settings: Settings = Settings()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
         val isVip = settings.getBoolean(KEY_VIP, false)
        val backgroundColor = if(isVip){(androidx.compose.ui.graphics.Color.Blue) }

        else
        {
            (androidx.compose.ui.graphics.Color.White)
        }

        Column(
            modifier =  Modifier.fillMaxSize().background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            val name = settings.getString(KEY_NAME,"")
            Text("Bienvenido $name", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Button(onClick = {
                settings.remove(KEY_NAME)
                settings.remove(KEY_VIP)
                settings.clear()
                navigator.pop()
            })
            {
                Text("Volver y borrar datos")
            }
        }

    }
}