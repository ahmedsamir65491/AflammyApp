package com.example.aflammy.domain.usecases.home.getData.series

import com.example.aflammy.data.repository.SeriesRepository
import com.example.aflammy.domain.mappers.series.TopRatedSeriesMapper
import com.example.aflammy.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopRatedTvShowSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val seriesMapper: TopRatedSeriesMapper,
) {

    suspend operator fun invoke(): Flow<List<Media>> {
        return seriesRepository.getTopRatedTvShow().map {
            it.map(seriesMapper::map)
        }
    }
}