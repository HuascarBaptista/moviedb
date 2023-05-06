package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.VideoApiResponse
import com.hebs.moviedb.domain.model.VideoMedia
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ApiResponseToVideoMediaMapperTest {

    private lateinit var mapper: ApiResponseToVideoMediaMapper

    @Before
    fun setUp() {
        mapper = ApiResponseToVideoMediaMapper()
    }

    @Test
    fun `map should convert VideoApiResponse to VideoMedia`() {
        val apiResponse = VideoApiResponse("1", "key", "name", "2023-04-29", 3)
        val expected = VideoMedia("1", "key", "name", "2023-04-29")

        val result = mapper.map(apiResponse)

        assertEquals(expected, result)
    }
}
