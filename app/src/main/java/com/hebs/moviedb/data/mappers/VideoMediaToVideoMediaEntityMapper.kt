package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.VideoMediaEntity
import com.hebs.moviedb.domain.model.VideoMedia
import javax.inject.Inject

class VideoMediaToVideoMediaEntityMapper @Inject constructor() {
    fun map(videoMedia: VideoMedia, resourceId: Int) =
        VideoMediaEntity(
            id = videoMedia.id,
            key = videoMedia.key,
            name = videoMedia.name,
            publishedAt = videoMedia.publishedAt,
            resourceId = resourceId
        )
}