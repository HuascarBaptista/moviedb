package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.VideoMedia
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Repository which getes data from Remote or Local data sources
 */
interface TvShowRepository {
    fun getPopularTvShowsSection(): Single<ResourceSection>

    fun getTopRatedTvShowsSection(): Single<ResourceSection>

    fun getVideoMedia(resourceId: Int): Single<List<VideoMedia>>

    fun search(query: String): Observable<ResourceSection>
}