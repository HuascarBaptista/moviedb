package com.hebs.moviedb.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hebs.moviedb.domain.cases.SearchUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.SectionType
import com.hebs.moviedb.domain.model.actions.SearchSectionActions
import com.hebs.moviedb.presentation.RxSchedulerRule
import com.hebs.moviedb.presentation.base.BaseViewActions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    private lateinit var searchUseCase: SearchUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(searchUseCase)
    }

    @Test
    fun `when search called, emits actions`() {
        val query = "test"
        val resourceSection = ResourceSection(
            categoryName = NAME,
            categoryType = TYPE,
            resources = listOf()
        )
        every { searchUseCase.search(query) } returns Observable.just(resourceSection)

        val observer = mockk<Observer<SearchSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.searchLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.search(query)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observer.onChanged(ofType(SearchSectionActions.UpdateSearch::class)) }
    }

    @Test
    fun `when search called and fails, emits error`() {
        val query = "test"
        every { searchUseCase.search(query) } returns Observable.error(Throwable())

        val observer = mockk<Observer<SearchSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.searchLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)


        viewModel.search(query)

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.Error::class)) }
    }

    private companion object {
        const val NAME = "NAME"
        val TYPE = SectionType.SEARCH_MIX
    }
}