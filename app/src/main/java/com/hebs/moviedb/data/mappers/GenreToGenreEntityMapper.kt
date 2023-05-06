package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.GenreEntity
import com.hebs.moviedb.domain.model.Genre
import javax.inject.Inject

class GenreToGenreEntityMapper @Inject constructor() {
    fun map(genre: Genre) =
        GenreEntity(
            id = genre.id,
            name = genre.name
        )
}