package com.example.aflammy.data.remote.response

import com.example.aflammy.data.remote.response.episode.EpisodeDto
import com.google.gson.annotations.SerializedName

data class SeasonDto(
    @SerializedName("air_date")
    val airDate: String? = null,
    @SerializedName("episode_count")
    val episodeCount: Int? = null,
    @SerializedName("episodes")
    val episodes: List<EpisodeDto>? = null,
    @SerializedName("_id")
    val idString: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("season_number")
    val seasonNumber: Int? = null
)