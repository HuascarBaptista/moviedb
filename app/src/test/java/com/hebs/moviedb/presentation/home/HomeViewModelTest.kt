package com.hebs.moviedb.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hebs.moviedb.domain.cases.HomeUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.presentation.RxSchedulerRule
import com.hebs.moviedb.presentation.base.BaseViewActions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxSchedulerRule()

    @MockK
    private lateinit var homeUseCase: HomeUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(homeUseCase)
    }

    @Test
    fun `when home called, emits actions`() {
        val resourceSection = listOf<ResourceSection>()
        every { homeUseCase.getData() } returns Flowable.just(resourceSection)

        val observer = mockk<Observer<HomeSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.sectionsLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.loadMovies()

        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observer.onChanged(ofType(HomeSectionActions.UpdateSections::class)) }
    }

    @Test
    fun `when home called and fails, emits error`() {
        every { homeUseCase.getData() } returns Flowable.error(Throwable())

        val observer = mockk<Observer<HomeSectionActions>>(relaxed = true)
        val observerBase = mockk<Observer<BaseViewActions>>(relaxed = true)
        viewModel.sectionsLiveData.observeForever(observer)
        viewModel.baseFragmentActionLiveData.observeForever(observerBase)

        viewModel.loadMovies()


        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.ShowLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.HideLoading::class)) }
        verify(exactly = 1) { observerBase.onChanged(ofType(BaseViewActions.Error::class)) }
    }
}