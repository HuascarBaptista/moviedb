package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.VideoMediaEntity
import com.hebs.moviedb.domain.model.VideoMedia
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class VideoMediaEntityToVideoMediaMapperTest {

    private lateinit var mapper: VideoMediaEntityToVideoMediaMapper

    @Before
    fun setUp() {
        mapper = VideoMediaEntityToVideoMediaMapper()
    }

    @Test
    fun `map should convert VideoMediaEntity to VideoMedia`() {
        val videoMediaEntity = VideoMediaEntity(
            id = "1",
            key = "example_key",
            name = "example_name",
            publishedAt = "2022-01-01",
            resourceId = 1
        )

        val expected = VideoMedia(
            id = "1",
            key = "example_key",
            name = "example_name",
            publishedAt = "2022-01-01"
        )

        val result = mapper.map(videoMediaEntity)

        assertEquals(expected, result)
    }
}