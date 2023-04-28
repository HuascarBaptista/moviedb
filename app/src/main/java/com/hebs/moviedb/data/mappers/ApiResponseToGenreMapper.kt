package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.GenresApiResponse
import com.hebs.moviedb.domain.model.Genre
import javax.inject.Inject

class ApiResponseToGenreMapper @Inject constructor() {
    fun map(genresApiResponse: GenresApiResponse) =
        Genre(
            id = genresApiResponse.id,
            name = genresApiResponse.name
        )
}