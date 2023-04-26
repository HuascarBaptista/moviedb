package com.hebs.moviedb.data.wrappers

import com.hebs.moviedb.data.model.VideoEntity
import com.hebs.moviedb.domain.model.VideoMedia

object VideoEntityToVideoMediaWrapper {
    fun fromEntity(entity: VideoEntity): VideoMedia {
        return VideoMedia(
            id = entity.id,
            key = entity.key,
            name = entity.name,
            publishedAt = entity.publishedAt
        )
    }
}