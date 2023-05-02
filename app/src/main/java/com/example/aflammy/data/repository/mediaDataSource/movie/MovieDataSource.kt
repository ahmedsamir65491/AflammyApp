package com.example.aflammy.data.repository.mediaDataSource.movie

import com.example.aflammy.data.remote.response.MovieDto
import com.example.aflammy.data.remote.service.MovieService
import com.example.aflammy.data.repository.BasePagingSource
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val service: MovieService
) : BasePagingSource<MovieDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: STANDARD_PAGE_NUMBER
        return try {
            val response = service.getAllMovies(pageNumber)

            LoadResult.Page(
                data = response.body()?.items as List<MovieDto>,
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}