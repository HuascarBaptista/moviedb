package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import com.hebs.moviedb.tools.applyIoSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    fun search(query: String): Observable<ResourceSection> {
        val safeQuery = query.trim()
        val searchMovies = moviesRepository.search(safeQuery)
        val searchTvShows = tvShowRepository.search(safeQuery)

        return Observable.zip(
            searchMovies,
            searchTvShows
        ) { movies: ResourceSection, tvShows: ResourceSection ->
            val resources = (movies.resources + tvShows.resources).distinct()
                .sortedByDescending { it.rating }
            ResourceSection(
                categoryType = SectionType.SEARCH_MIX,
                resources = resources,
                categoryName = movies.categoryName
            )
        }
            .applyIoSchedulers()
            .flatMap {
                moviesRepository.storeQueryResult(it)
                Observable.just(it)
            }
    }
}