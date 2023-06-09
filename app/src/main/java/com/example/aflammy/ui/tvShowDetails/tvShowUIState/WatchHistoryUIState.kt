package com.example.aflammy.ui.tvShowDetails.tvShowUIState

import com.example.aflammy.utilities.Constants


data class WatchHistoryUIState(
    val id: Int = 0,
    val posterPath: String = "",
    val movieTitle: String = "",
    val movieDuration: Int = 0,
    val voteAverage: String = "",
    val releaseDate: String = "",
    val mediaType: String = Constants.TV_SHOWS
)
