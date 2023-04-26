package com.hebs.moviedb.data.wrappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.TVShow

object ResourceEntityToTvShowWrapper {
    fun fromEntity(entity: ResourceEntity): TVShow {
        return TVShow(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            rating = entity.rating,
            posterImageURL = entity.posterImageURL,
            coverImageURL = entity.coverImageURL
        )
    }
}