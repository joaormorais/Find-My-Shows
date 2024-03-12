package com.example.findmyshows.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.findmyshows.services.TMDBService
import com.example.findmyshows.utils.Consts
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class TVShow(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
)

data class TVShowResponse(
    @SerializedName("results") val results: List<TVShow>
)

data class TVShowDetailsResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("backdrop_path") val img: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("origin_country") val originCountry: List<String>? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("number_of_episodes") val nEpisodes: String? = null,
    @SerializedName("number_of_seasons") val nSeasons: String? = null
)

data class TVShowKeywords(
    @SerializedName("name") val name: String?
)

data class TVShowKeywordsResponse(
    @SerializedName("results") val results: List<TVShowKeywords>
)

data class TVShowSeasonDetails(
    @SerializedName("name") val name: String?
)

data class TVShowSeasonDetailsResponse(
    @SerializedName("episodes") val episodes: List<TVShowSeasonDetails>
)

class ShowData {

    private val _tvShows = mutableStateOf(emptyList<TVShow>())
    val tvShows: MutableState<List<TVShow>>
        get() = _tvShows

    private val _tvShowDetails = mutableStateOf(TVShowDetailsResponse())
    val tvShowDetails: MutableState<TVShowDetailsResponse>
        get() = _tvShowDetails

    private val _tvShowKeywords = mutableStateOf(emptyList<TVShowKeywords>())
    val tvShowKeywords: MutableState<List<TVShowKeywords>>
        get() = _tvShowKeywords

    private val _tvShowSeasonDetails = mutableStateOf(emptyList<TVShowSeasonDetails>())
    private val _seasonMap = mutableStateOf(mutableMapOf<Int, List<String>>())
    val seasonMap: MutableState<MutableMap<Int, List<String>>>
        get() = _seasonMap


    private val api = Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBService::class.java)

    fun getPopularTVShows() {
        api.getPopularTVShows(Consts.API_KEY).enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                if (response.isSuccessful) {
                    val tvShowPopularResponse = response.body()
                    tvShowPopularResponse?.let {
                        _tvShows.value = it.results
                    }
                } else {
                    Log.i("ShowData", "getPopularTVShows onResponse erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.i("ShowData", "getPopularTVShows onFailure: ${t.message}")
            }
        })
    }

    fun getTVShow(showName: String) {
        api.getTVShow(showName, Consts.API_KEY).enqueue(object : Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                if (response.isSuccessful) {
                    val tvShowResponse = response.body()
                    tvShowResponse?.let {
                        _tvShows.value = it.results
                    }
                } else {
                    Log.i("ShowData", "getTVShow onResponse erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.i("ShowData", "getTVShow onFailure: ${t.message}")
            }

        })
    }

    fun getTVShowDetails(showId: String) {
        api.getTVShowDetails(showId, Consts.API_KEY)
            .enqueue(object : Callback<TVShowDetailsResponse> {
                override fun onResponse(
                    call: Call<TVShowDetailsResponse>,
                    response: Response<TVShowDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val tvShowDetailsResponse = response.body()
                        tvShowDetailsResponse?.let {
                            _tvShowDetails.value = it
                            Log.i(
                                "ShowData",
                                "getTVShowDetails onResponse: ${_tvShowDetails.value}"
                            )
                            Log.i(
                                "ShowData",
                                "imagem: ${_tvShowDetails.value.img}"
                            )

                            filterEpisodesBySeason(_tvShowDetails.value.id!!)
                            //_tvShowDetails.value.id?.let { it1 -> filterEpisodesBySeason(it1) }
                        }
                    } else {
                        Log.i("ShowData", "getTVShowDetails onResponse erro: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                    Log.i("ShowData", "getTVShowDetails onFailure: ${t.message}")
                }


            })
    }

    fun getTVShowKeywords(showId: String) {
        api.getTVShowKeywords(showId, Consts.API_KEY)
            .enqueue(object : Callback<TVShowKeywordsResponse> {
                override fun onResponse(
                    call: Call<TVShowKeywordsResponse>,
                    response: Response<TVShowKeywordsResponse>
                ) {
                    if (response.isSuccessful) {
                        val tvShowKeywordsResponse = response.body()
                        tvShowKeywordsResponse?.let {
                            _tvShowKeywords.value = it.results
                        }
                    } else {
                        Log.i("ShowData", "getTVShowKeywords onResponse erro: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<TVShowKeywordsResponse>, t: Throwable) {
                    Log.i("ShowData", "getTVShowKeywords onFailure: ${t.message}")
                }

            })
    }

    private fun getTVShowSeasonDetails(showId: String, seasonNumber: String) {
        api.getTVShowSeasonDetails(showId, seasonNumber, Consts.API_KEY)
            .enqueue(object : Callback<TVShowSeasonDetailsResponse> {
                override fun onResponse(
                    call: Call<TVShowSeasonDetailsResponse>,
                    response: Response<TVShowSeasonDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val tvShowSeasonDetailsResponse = response.body()
                        tvShowSeasonDetailsResponse?.let {
                            _tvShowSeasonDetails.value = it.episodes
                        }
                        Log.i(
                            "ShowData",
                            "$showId $seasonNumber getTVShowSeasonDetails onResponse certo: ${response.code()}"
                        )

                    } else {
                        Log.i(
                            "ShowData",
                            "$showId $seasonNumber getTVShowSeasonDetails onResponse erro: ${response.code()}"
                        )
                    }
                }

                override fun onFailure(call: Call<TVShowSeasonDetailsResponse>, t: Throwable) {
                    Log.i("ShowData", "getTVShowSeasonDetails onFailure: ${t.message}")
                }
            })
    }

    private fun filterEpisodesBySeason(itemId: String) {
        _seasonMap.value.clear()
        val nSeasonsInt = _tvShowDetails.value.nSeasons!!.toIntOrNull() ?: 0
        if (nSeasonsInt > 0)
            for (index in 1..nSeasonsInt) {
                getTVShowSeasonDetails(itemId, index.toString())
                val currentSeasonDetails = _tvShowSeasonDetails.value

                val nameList = mutableListOf<String>()
                currentSeasonDetails.forEach {
                    Log.d("ShowDetailsScreen", "it.name --> ${it.name}")
                    nameList.add(it.name!!)
                }

                Log.d(
                    "ShowDetailsScreen",
                    "testeeeeeeee lista --> ${index.toString()} ${nameList.toList()}"
                )
                _seasonMap.value[index] = nameList.toList()
            }

        Log.d("ShowDetailsScreen", "testeeeeeeee completo --> ${_seasonMap.value}")

    }

}