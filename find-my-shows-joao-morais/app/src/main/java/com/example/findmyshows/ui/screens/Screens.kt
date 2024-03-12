package com.example.findmyshows.ui.screens

enum class Screens(val path: String) {
    SEARCH_SHOWS("SearchShows"),
    SHOW_DETAILS("ShowDetails?itemId={itemId}");

    val route: String
        get() = this.path

}