package com.example.findmyshows.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.findmyshows.R
import com.example.findmyshows.data.TVShow
import com.example.findmyshows.ui.viewmodels.SearchShowsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchShowsScreen(
    searchShowsViewModel: SearchShowsViewModel,
    navController: NavHostController?,
    modifier: Modifier = Modifier
) {

    // utils for the lazy column
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // lazy column
    val showsDM = searchShowsViewModel.getTVShows()
    var showsUI by remember {
        searchShowsViewModel.getPopularTVShows()
        mutableStateOf<List<TVShow>>(emptyList())
    }
    LaunchedEffect(key1 = showsDM, block = {
        showsUI = showsDM.toList()
    })

    // search box
    var showName by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = showName,
                onValueChange = { showName = it },
                label = { Text(stringResource(R.string.show_name)) },
                modifier = modifier
                    .widthIn(max = 300.dp)
                    .fillMaxWidth()
            )
            IconButton(
                onClick = {
                    if (showName.isNotBlank()) {
                        showsUI = searchShowsViewModel.getTVShow(showName)
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    }
                }
            ) {
                Icon(
                    Icons.Default.Search,
                    "search",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(
                onClick = {
                        showsUI = searchShowsViewModel.getPopularTVShows()
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                }
            ) {
                Icon(
                    Icons.Default.Home,
                    "home",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Spacer(modifier.width(12.dp))

        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {

            items(showsUI.size) { index ->
                val show = showsUI[index]
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = {
                        navController?.navigate("ShowDetails?itemId=${show.id}")
                    }
                ) {
                    Text(text = show.name!!, modifier = modifier.padding(20.dp))
                }
            }

        }
    }
}