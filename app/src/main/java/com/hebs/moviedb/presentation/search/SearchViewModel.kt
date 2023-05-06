package com.hebs.moviedb.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hebs.moviedb.domain.cases.SearchUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.SearchSectionActions
import com.hebs.moviedb.presentation.base.BaseViewModel
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    private val _searchLiveData: MutableLiveData<SearchSectionActions> = MutableLiveData()
    val searchLiveData: LiveData<SearchSectionActions> = _searchLiveData

    fun search(query: String) {
        showLoading()
        var searchResults: Set<ResourceSection> = setOf()
        disposable.add(
            searchUseCase.search(query)
                .applySchedulers()
                .subscribe({
                    hideLoading()
                    searchResults = searchResults + it
                    _searchLiveData.postValue(
                        SearchSectionActions.UpdateSearch(
                            searchResults
                        )
                    )
                },
                    {
                        hideLoading()
                        sendError()
                    }
                )
        )
    }
}