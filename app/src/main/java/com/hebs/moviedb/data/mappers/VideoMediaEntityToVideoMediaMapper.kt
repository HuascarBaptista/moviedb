package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.VideoMediaEntity
import com.hebs.moviedb.domain.model.VideoMedia
import javax.inject.Inject

class VideoMediaEntityToVideoMediaMapper @Inject constructor() {
    fun map(videoMediaEntity: VideoMediaEntity) =
        VideoMedia(
            id = videoMediaEntity.id,
            key = videoMediaEntity.key,
            name = videoMediaEntity.name,
            publishedAt = videoMediaEntity.publishedAt,
        )
}