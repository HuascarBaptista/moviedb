package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.TVShow
import com.hebs.moviedb.domain.model.VideoMedia
import io.reactivex.rxjava3.core.Single

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface TVShowRepository {
    suspend fun fetchPopularTVShows(): Single<List<TVShow>>?

    suspend fun fetchTopRatedTVShows(): Single<List<TVShow>>?

    suspend fun getTVShowVideos(id: Int): Single<List<VideoMedia>>?

    suspend fun search(term: String): Single<List<TVShow>>
}