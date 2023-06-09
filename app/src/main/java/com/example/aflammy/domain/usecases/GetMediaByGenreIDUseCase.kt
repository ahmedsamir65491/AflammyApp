package com.example.aflammy.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.aflammy.data.repository.MovieRepository
import com.example.aflammy.data.repository.SeriesRepository
import com.example.aflammy.domain.mappers.movie.MovieMapper
import com.example.aflammy.domain.mappers.series.TVShowMapper
import com.example.aflammy.domain.models.Media
import com.example.aflammy.utilities.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetMediaByGenreIDUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TVShowMapper
) {

    suspend operator fun invoke(mediaType: Int, genreID: Int): Flow<PagingData<Media>> {
        return when (mediaType) {
            Constants.MOVIE_CATEGORIES_ID -> {
                getMovies(genreID)
            }
            else -> {
                getTVShows(genreID)
            }
        }
    }

    private suspend fun getMovies(genreID: Int): Flow<PagingData<Media>> {
        return if (genreID == Constants.FIRST_CATEGORY_ID) {
            wrapper(movieRepository::getAllMovies, movieMapper::map)
        } else {
            wrapper({ movieRepository.getMovieByGenre(genreID) }, movieMapper::map)
        }
    }

    private suspend fun getTVShows(genreID: Int): Flow<PagingData<Media>> {
        return if (genreID == Constants.FIRST_CATEGORY_ID) {
            wrapper(seriesRepository::getAllTVShows, tvShowMapper::map)

        } else {
            wrapper({ seriesRepository.getTVShowByGenre(genreID) }, tvShowMapper::map)
        }
    }

    private suspend fun <T : Any> wrapper(
        data: suspend () -> Pager<Int, T>,
        mapper: (T) -> Media,
    ): Flow<PagingData<Media>> {
        return data().flow.map { pager -> pager.map { mapper(it) } }
    }
}