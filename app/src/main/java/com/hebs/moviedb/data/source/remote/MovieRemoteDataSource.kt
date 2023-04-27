package com.hebs.moviedb.data.source.remote

import android.content.Context
import com.hebs.moviedb.R
import com.hebs.moviedb.data.mappers.ResourceEntityToSectionEntityMapper
import com.hebs.moviedb.data.mappers.VideoEntityToVideoMediaMapper
import com.hebs.moviedb.data.model.SectionEntity
import com.hebs.moviedb.data.model.SectionEntityType
import com.hebs.moviedb.data.remote.service.MovieService
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.tools.applySchedulers
import com.hebs.moviedb.tools.mapResultApplySchedules
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

open class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService,
    private val mapper: ResourceEntityToSectionEntityMapper,
    private val videoMapper: VideoEntityToVideoMediaMapper,
    private val context: Context
) {

    fun getPopularMovies() =
        movieService.getPopularMovies().map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_popular_movies),
                categoryType = SectionEntityType.POPULAR_MOVIES
            )
        }.applySchedulers()

    fun getTopRatedMovies(): Single<SectionEntity> =
        movieService.getTopRatedMovies().map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_top_rated_movies),
                categoryType = SectionEntityType.TOP_RATED_MOVIES
            )
        }.applySchedulers()

    fun getMovieVideos(id: Int): Single<List<VideoMedia>> =
        movieService.getMovieMedia(id).mapResultApplySchedules(videoMapper)

    fun search(term: String): Single<SectionEntity> =
        movieService.search(term).map {
            mapper.fromEntity(
                entity = it,
                categoryName = context.getString(R.string.section_title_search),
                categoryType = SectionEntityType.SEARCH
            )
        }.applySchedulers()
}

