// MainScreen.kt
package com.aryan.reader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

sealed class BottomBarScreen(val route: String, val label: String, val iconResId: Int) {
    object Home : BottomBarScreen("home", "Home", R.drawable.home)
    object Library : BottomBarScreen("library", "Library", R.drawable.library_books)
}

private val bottomBarItems = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Library,
)

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    windowSizeClass: WindowSizeClass,
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val viewingShelfName = uiState.viewingShelfName

    if (viewingShelfName != null) {
        ShelfScreen(viewModel = viewModel)
    } else {
        val pagerState = rememberPagerState(
            initialPage = uiState.mainScreenStartPage,
            pageCount = { bottomBarItems.size }
        )
        val scope = rememberCoroutineScope()

        LaunchedEffect(pagerState.currentPage) {
            viewModel.setMainScreenPage(pagerState.currentPage)
        }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottomBarItems.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            icon = { Icon(painterResource(id = screen.iconResId), contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                key = { bottomBarItems[it].route },
                beyondViewportPageCount = 1,
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        windowSizeClass = windowSizeClass,
                        navController = navController
                    )
                    1 -> LibraryScreen(viewModel = viewModel)
                }
            }
        }
    }
}