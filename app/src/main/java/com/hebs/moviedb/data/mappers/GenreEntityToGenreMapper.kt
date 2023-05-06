package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.GenreEntity
import com.hebs.moviedb.domain.model.Genre
import javax.inject.Inject

class GenreEntityToGenreMapper @Inject constructor() {
    fun map(genreEntity: GenreEntity) =
        Genre(
            id = genreEntity.id,
            name = genreEntity.name
        )
}