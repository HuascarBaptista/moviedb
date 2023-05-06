package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.VideoMediaEntity
import com.hebs.moviedb.domain.model.VideoMedia
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class VideoMediaToVideoMediaEntityMapperTest {

    private lateinit var mapper: VideoMediaToVideoMediaEntityMapper

    @Before
    fun setUp() {
        mapper = VideoMediaToVideoMediaEntityMapper()
    }

    @Test
    fun `map should convert VideoMedia to VideoMediaEntity`() {
        val videoMedia = VideoMedia(
            id = "1",
            key = "example_key",
            name = "example_name",
            publishedAt = "2022-01-01"
        )
        val resourceId = 1

        val expected = VideoMediaEntity(
            id = "1",
            key = "example_key",
            name = "example_name",
            publishedAt = "2022-01-01",
            resourceId = resourceId
        )

        val result = mapper.map(videoMedia, resourceId)

        assertEquals(expected, result)
    }
}