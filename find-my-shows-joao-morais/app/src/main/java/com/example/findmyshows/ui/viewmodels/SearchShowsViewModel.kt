package com.example.findmyshows.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.findmyshows.data.TVShow
import com.example.findmyshows.data.ShowData

class SearchShowsViewModelFactory(
    private val showData: ShowData
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchShowsViewModel(showData) as T
    }
}

class SearchShowsViewModel(
    private val showData: ShowData
) : ViewModel() {

    fun getTVShows(): List<TVShow> {
        return showData.tvShows.value
    }

    fun getPopularTVShows(): List<TVShow> {
        showData.getPopularTVShows()
        return showData.tvShows.value
    }

    fun getTVShow(showName: String): List<TVShow> {
        showData.getTVShow(showName)
        return showData.tvShows.value
    }

}