package com.example.aflammy.ui.actorDetails.mappers

import com.example.aflammy.domain.mappers.Mapper
import com.example.aflammy.domain.models.ActorDetails
import com.example.aflammy.ui.actorDetails.models.ActorDetailsUIState
import javax.inject.Inject

class ActorDetailsUIMapper @Inject constructor() : Mapper<ActorDetails, ActorDetailsUIState> {
    override fun map(input: ActorDetails): ActorDetailsUIState {
        return ActorDetailsUIState(
            name = input.actorName,
            imageUrl = input.actorImage,
            gender = input.actorGender,
            birthday = input.actorBirthday,
            biography = input.actorBiography,
            placeOfBirth = input.actorPlaceOfBirth,
            knownFor = input.knownForDepartment,
        )
    }
}