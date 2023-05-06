package com.hebs.moviedb.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hebs.moviedb.domain.cases.DetailUseCase
import com.hebs.moviedb.domain.model.TvShow
import com.hebs.moviedb.domain.model.actions.DetailViewActions
import com.hebs.moviedb.presentation.RxSchedulerRule
import com.hebs.moviedb.presentation.base.BaseViewActions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    private lateinit var resource: TvShow

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    private lateinit var detailUseCase: DetailUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(detailUseCase)
        resource = TvShow(
            id = ID,
            title = TITLE,
            overview = OVERVIEW,
            rating = RATING,
            score = SCORE,
            posterImageUrl = POSTER_IMAGE_URL,
            coverImageUrl = COVER_IMAGE_URL,
            isMovie = IS_MOVIE
        )

    }

    @Test
    fun `when videos called, emits actions`() {
        every { detailUseCase.getVideosMedia(resource) } returns Single.just(listOf())

        val observer = mockk<Observer<DetailViewActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.videoMediaLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.loadMovies(resource)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observer.onChanged(ofType(DetailViewActions.UpdateVideos::class)) }
    }

    @Test
    fun `when videos called and fails, emits error`() {
        every { detailUseCase.getVideosMedia(resource) } returns Single.error(Throwable())

        val observer = mockk<Observer<DetailViewActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.videoMediaLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.loadMovies(resource)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.Error::class)) }
    }

    private companion object {
        const val ID = 1
        const val TITLE = "Example TV Show"
        const val OVERVIEW = "This is an example TV show."
        const val RATING = 8.5
        const val SCORE = 90.0
        const val POSTER_IMAGE_URL = "https://example.com/poster.jpg"
        const val COVER_IMAGE_URL = "https://example.com/cover.jpg"
        const val IS_MOVIE = false
    }
}