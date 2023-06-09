package com.example.aflammy.ui.home.models

import com.example.aflammy.domain.enums.AllMediaType

sealed interface HomeUIEvent {
    object ClickSeeAllActorEvent : HomeUIEvent
    data class ClickMovieEvent(val movieID: Int) : HomeUIEvent
    data class ClickActorEvent(val actorID: Int) : HomeUIEvent
    data class ClickSeriesEvent(val seriesID: Int) : HomeUIEvent
    data class ClickSeeAllMovieEvent(val mediaType: AllMediaType) : HomeUIEvent
    data class ClickSeeAllTVShowsEvent(val mediaType: AllMediaType) : HomeUIEvent
}