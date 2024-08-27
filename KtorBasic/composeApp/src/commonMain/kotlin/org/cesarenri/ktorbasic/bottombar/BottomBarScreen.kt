package org.cesarenri.ktorbasic.bottombar

import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class BottomBarScreen: Screen {

    @Composable
    override fun Content() {
        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    it, listOf(HomeTab, FavTab, ProfileTab)
                )
            }
        )
        {
            Scaffold(
                topBar = {
                    TopAppBar(title = {"BeliVGames"})
                }
            ){}
        }
    }
}