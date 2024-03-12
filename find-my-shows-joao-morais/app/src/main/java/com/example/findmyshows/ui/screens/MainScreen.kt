package com.example.findmyshows.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.findmyshows.MyApplication
import com.example.findmyshows.R
import com.example.findmyshows.ui.viewmodels.SearchShowsViewModel
import com.example.findmyshows.ui.viewmodels.SearchShowsViewModelFactory
import com.example.findmyshows.ui.viewmodels.ShowDetailsViewModel
import com.example.findmyshows.ui.viewmodels.ShowDetailsViewModelFactory
import com.example.findmyshows.utils.Consts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    val app = context.applicationContext as MyApplication

    var searchShowsViewModel: SearchShowsViewModel?
    var showDetailsViewModel: ShowDetailsViewModel?

    var isSearchShowsScreen by remember { mutableStateOf(false) }

    var screenTitle by remember { mutableStateOf("") }
    val searchShowsTitle = stringResource(R.string.search_shows)
    val showDetailsTitle = stringResource(R.string.show_details)

    navController.addOnDestinationChangedListener { controller, destination, arguments ->

        isSearchShowsScreen = (destination.route == Screens.SEARCH_SHOWS.route)

        when (destination.route) {

            Screens.SEARCH_SHOWS.route -> {
                screenTitle = searchShowsTitle
            }

            Screens.SHOW_DETAILS.route -> {
                screenTitle = showDetailsTitle
            }

        }

    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(screenTitle, color = Color.White)
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (isSearchShowsScreen) {
                                    (navController.context as? Activity)?.finishAffinity()
                                } else {
                                    navController.navigateUp()
                                }
                            }                        ) {
                            Icon(
                                imageVector = if (isSearchShowsScreen) Icons.Filled.ExitToApp else Icons.Filled.ArrowBack,
                                contentDescription = if (isSearchShowsScreen) "Quit" else "Back"
                            )
                        }
                    },

                    actions = {},

                    colors = smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.inversePrimary,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                )
            },
            modifier = modifier.fillMaxSize()
        ) {

            NavHost(
                navController = navController,
                startDestination = Screens.SEARCH_SHOWS.route,
                modifier = modifier
                    .padding(it)
            ) {

                composable(Screens.SEARCH_SHOWS.route) {
                    searchShowsViewModel =
                        viewModel(factory = SearchShowsViewModelFactory(app.showData))
                    SearchShowsScreen(
                        searchShowsViewModel!!,
                        navController
                    )
                }

                composable(Screens.SHOW_DETAILS.route,
                    arguments = listOf(
                        navArgument("itemId") {
                            type = NavType.StringType
                            defaultValue = Consts.DEFAULT_VALUE
                            nullable = false
                        }
                    )
                ) {

                    val itemId = it.arguments?.getString("itemId")
                    showDetailsViewModel =
                        viewModel(factory = ShowDetailsViewModelFactory(app.showData))
                    ShowDetailsScreen(
                        showDetailsViewModel!!,
                        itemId!!
                    )
                }

            }

        }
    }

}