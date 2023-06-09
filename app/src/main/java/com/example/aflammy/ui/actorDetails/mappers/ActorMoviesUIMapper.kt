package com.example.aflammy.ui.actorDetails.mappers

import com.example.aflammy.domain.mappers.Mapper
import com.example.aflammy.domain.models.ActorMovie
import com.example.aflammy.ui.actorDetails.models.ActorMoviesUIState
import javax.inject.Inject

class ActorMoviesUIMapper @Inject constructor() : Mapper<ActorMovie, ActorMoviesUIState> {
    override fun map(input: ActorMovie): ActorMoviesUIState {
        return ActorMoviesUIState(
            id = input.movieId,
            imageUrl = input.movieImage
        )
    }
}