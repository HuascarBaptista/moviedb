package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.TVShow
import javax.inject.Inject

class ResourceEntityToTvShowMapper @Inject constructor() : MapperBase<ResourceEntity, TVShow> {
    override fun fromEntity(entity: ResourceEntity): TVShow {
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