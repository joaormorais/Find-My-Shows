package com.example.findmyshows.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.findmyshows.R
import com.example.findmyshows.ui.viewmodels.ShowDetailsViewModel
import com.example.findmyshows.utils.Consts

@Composable
fun ShowDetailsScreen(
    showDetailsViewModel: ShowDetailsViewModel,
    itemId: String,
    modifier: Modifier = Modifier
) {

    val detailsDM = showDetailsViewModel.getTVShowDetails(itemId)
    var detailsUI by remember {
        mutableStateOf(showDetailsViewModel.getTVShowDetails(itemId))
    }
    LaunchedEffect(key1 = detailsDM, block = {
        detailsUI = detailsDM
    })

    val keywordsDM = showDetailsViewModel.getTVShowKeywords(itemId)
    var keywordsUI by remember {
        mutableStateOf(showDetailsViewModel.getTVShowKeywords(itemId))
    }
    LaunchedEffect(key1 = keywordsDM, block = {
        keywordsUI = keywordsDM.toList()
    })

    val episodesFilteredDM = showDetailsViewModel.getEpisodesFiltered()
    var episodesFilteredUI by remember {
        mutableStateOf(showDetailsViewModel.getEpisodesFiltered())
    }
    LaunchedEffect(key1 = episodesFilteredDM, block = {
        episodesFilteredUI = episodesFilteredDM
        Log.d("ShowDetailsScreen", "LaunchedEffect mapa final --> $episodesFilteredUI")
    })

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {

        item {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(8.dp)
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.name) + detailsUI.name,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val imagePainter =
                            rememberAsyncImagePainter(Consts.IMAGE_BASE_URL + detailsUI.img)
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = imagePainter,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.overview) + detailsUI.overview,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.origin_country) + detailsUI.originCountry,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(
                            R.string.original_language
                        ) + detailsUI.originalLanguage,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.number_of_episodes) + detailsUI.nEpisodes,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.number_of_seasons) + detailsUI.nSeasons,
                        modifier = modifier.padding(20.dp)
                    )
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(
                        text = stringResource(R.string.keywords),
                        modifier = modifier.padding(
                            start = 20.dp,
                            end = 1.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        )
                    )
                    keywordsUI.forEach {
                        Text(
                            text = it.name!!,
                            modifier = modifier.padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        )
                    }
                }
                Spacer(modifier.width(12.dp))
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 4.dp),
                    border = BorderStroke(2.dp, Color.Black)
                ) {

                    episodesFilteredUI.forEach { (season, episodes) ->
                        Text(
                            text = stringResource(R.string.season, season),
                            modifier = modifier.padding(8.dp)
                        )
                        episodes.forEach { episode ->
                            Text(
                                text = episode,
                                modifier = modifier.padding(4.dp)
                            )
                        }
                    }

                }

            }

        }

    }

}