package com.hebs.moviedb.presentation.genres

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hebs.moviedb.domain.cases.GenreUseCase
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.RxSchedulerRule
import com.hebs.moviedb.presentation.base.BaseViewActions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GenreViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    private lateinit var genreUseCase: GenreUseCase

    private lateinit var viewModel: GenreViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = GenreViewModel(genreUseCase)
    }

    @Test
    fun `when byGenre called, emits actions`() {
        val genre = Genre(1, NAME)
        val resourceSection = ResourceSection(
            categoryName = NAME,
            categoryType = TYPE,
            resources = listOf()
        )
        every { genreUseCase.getByGenre(genre) } returns Flowable.just(resourceSection)

        val observer = mockk<Observer<GenreSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.genresLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.getByGenre(genre)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observer.onChanged(ofType(GenreSectionActions.UpdateSections::class)) }
    }

    @Test
    fun `when byGenre called and fails, emits error`() {
        val genre = Genre(1, NAME)
        every { genreUseCase.getByGenre(genre) } returns Flowable.error(Throwable())

        val observer = mockk<Observer<GenreSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.genresLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)


        viewModel.getByGenre(genre)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.Error::class)) }
    }

    @Test
    fun `when genre called, emits actions`() {
        every { genreUseCase.getGenreList() } returns Single.just(listOf())

        val observer = mockk<Observer<GenreSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.genresLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.getGenres()

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observer.onChanged(ofType(GenreSectionActions.UpdateGenres::class)) }
    }

    @Test
    fun `when genre called and fails, emits error`() {
        every { genreUseCase.getGenreList() } returns Single.error(Throwable())

        val observer = mockk<Observer<GenreSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.genresLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.getGenres()

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.Error::class)) }

    }

    private companion object {
        const val NAME = "NAME"
        val TYPE = SectionType.BY_GENRE_MOVIES
    }
}