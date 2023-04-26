package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.Movie
import javax.inject.Inject

class ResourceEntityToMovieMapper @Inject constructor() : MapperBase<ResourceEntity, Movie> {
    override fun fromEntity(entity: ResourceEntity): Movie {
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