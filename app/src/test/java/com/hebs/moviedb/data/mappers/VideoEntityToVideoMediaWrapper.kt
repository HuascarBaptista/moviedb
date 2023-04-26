package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.VideoEntity
import com.hebs.moviedb.domain.model.VideoMedia
import junit.framework.TestCase
import org.junit.Test


class VideoEntityToVideoMediaMapperTest {

    @Test
    fun `fromEntity should map VideoEntity to VideoMedia correctly`() {
        val entity = VideoEntity(
            id = "135",
            key = "Hebs Title",
            name = "Hebs Overview",
            publishedAt = "8.5",
            resourceId = 135
        )

        val result: VideoMedia = VideoEntityToVideoMediaMapper().fromEntity(entity)
        TestCase.assertEquals(entity.id, result.id)
        TestCase.assertEquals(entity.key, result.key)
        TestCase.assertEquals(entity.name, result.name)
        TestCase.assertEquals(entity.publishedAt, result.publishedAt)
    }
}