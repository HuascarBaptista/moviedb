package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.VideoApiResponse
import com.hebs.moviedb.domain.model.VideoMedia
import javax.inject.Inject

class VideoEntityToVideoMediaMapper @Inject constructor() {
    fun map(entity: VideoApiResponse): VideoMedia {
        return VideoMedia(
            id = entity.id,
            key = entity.key,
            name = entity.name,
            publishedAt = entity.publishedAt
        )
    }
}