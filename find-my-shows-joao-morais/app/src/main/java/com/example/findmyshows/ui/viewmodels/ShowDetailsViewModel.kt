package com.example.findmyshows.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findmyshows.data.ShowData
import com.example.findmyshows.data.TVShowDetailsResponse
import com.example.findmyshows.data.TVShowKeywords

class ShowDetailsViewModelFactory(
    private val showData: ShowData
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShowDetailsViewModel(showData) as T
    }
}

class ShowDetailsViewModel(
    private val showData: ShowData
) : ViewModel() {

    fun getTVShowDetails(showId: String): TVShowDetailsResponse {
        showData.getTVShowDetails(showId)
        return showData.tvShowDetails.value
    }

    fun getTVShowKeywords(showId: String): List<TVShowKeywords> {
        showData.getTVShowKeywords(showId)
        return showData.tvShowKeywords.value
    }

    fun getEpisodesFiltered(): Map<Int, List<String>> {
        return showData.seasonMap.value
    }

}