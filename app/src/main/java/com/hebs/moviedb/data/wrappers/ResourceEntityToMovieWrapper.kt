package com.hebs.moviedb.data.wrappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.Movie

object ResourceEntityToMovieWrapper {
    fun fromEntity(entity: ResourceEntity): Movie {
        return Movie(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            rating = entity.rating,
            posterImageURL = entity.posterImageURL,
            coverImageURL = entity.coverImageURL
        )
    }
}