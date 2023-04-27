package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.ResourceEntity
import com.hebs.moviedb.domain.model.TvShow
import javax.inject.Inject

class ResourceEntityToTvShowMapper @Inject constructor() : MapperBase<ResourceEntity, TvShow> {
    override fun fromEntity(entity: ResourceEntity): TvShow {
        return TvShow(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            rating = entity.rating,
            posterImageURL = entity.posterImageURL,
            coverImageURL = entity.coverImageURL
        )
    }
}