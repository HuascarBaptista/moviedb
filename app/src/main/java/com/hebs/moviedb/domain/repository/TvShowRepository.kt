package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.ResourceSection
import io.reactivex.rxjava3.core.Single

/**
 * Repository which getes data from Remote or Local data sources
 */
interface TvShowRepository {
    fun getPopularTvShowsSection(): Single<ResourceSection>

    fun getTopRatedTvShowsSection(): Single<ResourceSection>

    fun getTvShowVideos(id: Int)

    fun search(term: String): Single<ResourceSection>
}