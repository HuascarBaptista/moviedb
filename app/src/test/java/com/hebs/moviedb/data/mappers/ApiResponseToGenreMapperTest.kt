package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.api.GenresApiResponse
import com.hebs.moviedb.domain.model.Genre
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ApiResponseToGenreMapperTest {

    private lateinit var mapper: ApiResponseToGenreMapper

    @Before
    fun setUp() {
        mapper = ApiResponseToGenreMapper()
    }

    @Test
    fun `map should convert GenresApiResponse to Genre`() {
        val apiResponse = GenresApiResponse(1, "Action")
        val expected = Genre(1, "Action")

        val result = mapper.map(apiResponse)

        assertEquals(expected, result)
    }
}