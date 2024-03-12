package com.example.findmyshows.services

import com.example.findmyshows.data.TVShowDetailsResponse
import com.example.findmyshows.data.TVShowKeywordsResponse
import com.example.findmyshows.data.TVShowResponse
import com.example.findmyshows.data.TVShowSeasonDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("tv/popular") // https://api.themoviedb.org/3/tv/popular?api_key=39a3c712614c598a6d5ca7a7c35a3ab1
    fun getPopularTVShows(
        @Query("api_key") apiKey: String
    ): Call<TVShowResponse>

    @GET("search/tv") // https://api.themoviedb.org/3/search/tv?query=Long%20Road-in-the-Dunes&api_key=39a3c712614c598a6d5ca7a7c35a3ab1
    fun getTVShow(
        @Query("query") name: String,
        @Query("api_key") apiKey: String
    ): Call<TVShowResponse>

    @GET("tv/{id}")
    fun getTVShowDetails( // https://api.themoviedb.org/3/tv/1399?api_key=39a3c712614c598a6d5ca7a7c35a3ab1
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<TVShowDetailsResponse>

    @GET("tv/{id}/keywords") // https://api.themoviedb.org/3/tv/1399/keywords?api_key=39a3c712614c598a6d5ca7a7c35a3ab1
    fun getTVShowKeywords(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): Call<TVShowKeywordsResponse>

    @GET("tv/{id}/season/{season_number}") // https://api.themoviedb.org/3/tv/1399/season/1?api_key=39a3c712614c598a6d5ca7a7c35a3ab1
    fun getTVShowSeasonDetails(
        @Path("id") id: String,
        @Path("season_number") seasonNumber: String,
        @Query("api_key") apiKey: String
    ): Call<TVShowSeasonDetailsResponse>

}