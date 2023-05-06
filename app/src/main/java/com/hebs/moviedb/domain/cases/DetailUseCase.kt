package com.hebs.moviedb.domain.cases

import com.hebs.moviedb.domain.model.Movie
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.domain.repository.MovieRepository
import com.hebs.moviedb.domain.repository.TvShowRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val moviesRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) {
    fun getVideosMedia(resource: Resource): Single<List<VideoMedia>> {
        return if (resource is Movie) {
            moviesRepository.getVideoMedia(resource.id)
        } else {
            tvShowRepository.getVideoMedia(resource.id)
        }
    }
}