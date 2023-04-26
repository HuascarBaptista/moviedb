package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.VideoEntity
import com.hebs.moviedb.domain.model.VideoMedia
import javax.inject.Inject

class VideoEntityToVideoMediaMapper @Inject constructor() : MapperBase<VideoEntity, VideoMedia> {
    override fun fromEntity(entity: VideoEntity): VideoMedia {
        return VideoMedia(
            id = entity.id,
            key = entity.key,
            name = entity.name,
            publishedAt = entity.publishedAt
        )
    }
}