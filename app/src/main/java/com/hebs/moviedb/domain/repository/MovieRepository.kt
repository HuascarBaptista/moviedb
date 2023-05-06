package com.hebs.moviedb.domain.repository

import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.VideoMedia
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/**
 * Repository which fetches data from Remote or Local data sources
 */
interface MovieRepository {

    fun getPopularMoviesSection(): Single<ResourceSection>

    fun getTopRatedMoviesSection(): Single<ResourceSection>

    fun getVideoMedia(resourceId: Int): Single<List<VideoMedia>>

    fun search(query: String): Observable<ResourceSection>
    fun storeQueryResult(resourceSection: ResourceSection)
    fun storeGenreList(genreList: List<Genre>)
    fun getGenreList(): Single<List<Genre>>
    fun getByGenre(genre: Genre): Single<ResourceSection>
}