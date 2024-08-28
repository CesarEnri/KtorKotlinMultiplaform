package org.cesarenri.ktorbasic.bottombar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class BottomBarScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
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
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = {
                    NavigationBar{
                        val tabNavigator = LocalTabNavigator.current

                        NavigationBarItem(
                            selected = tabNavigator.current.key == HomeTab.key,
                            label = {Text(HomeTab.options.title)},
                            icon = { Icon(painter = HomeTab.options.icon!!, contentDescription = null) },
                            onClick = {
                                tabNavigator.current = HomeTab
                            }
                        )

                        NavigationBarItem(
                            selected = tabNavigator.current.key == FavTab.key,
                            label = {Text(FavTab.options.title)},
                            icon = { Icon(painter = FavTab.options.icon!!, contentDescription = null) },
                            onClick = {
                                tabNavigator.current = FavTab
                            }
                        )

                        NavigationBarItem(
                            selected = tabNavigator.current.key == ProfileTab.key,
                            label = {Text(ProfileTab.options.title)},
                            icon = { Icon(painter = ProfileTab.options.icon!!, contentDescription = null) },
                            onClick = {
                                tabNavigator.current = ProfileTab
                            }
                        )
                    }
                },
                content = { CurrentTab() }
            )
        }
    }
}