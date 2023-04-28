package com.hebs.moviedb.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hebs.moviedb.domain.cases.SearchUseCase
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.SearchSectionActions
import com.hebs.moviedb.tools.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _searchLiveData: MutableLiveData<SearchSectionActions> = MutableLiveData()
    val searchLiveData: LiveData<SearchSectionActions> = _searchLiveData

    fun search(query: String) {
        var searchResults: Set<ResourceSection> = setOf()
        disposable.add(
            searchUseCase.search(query)
                .doOnSubscribe {
                    _searchLiveData.postValue(SearchSectionActions.ShowLoading)
                }
                .doOnTerminate {
                    _searchLiveData.postValue(SearchSectionActions.HideLoading)
                }
                .applySchedulers()
                .subscribe({
                    searchResults = searchResults + it
                    _searchLiveData.postValue(
                        SearchSectionActions.UpdateSearch(
                            searchResults
                        )
                    )
                },
                    {
                        _searchLiveData.postValue(it.message?.let { it1 ->
                            SearchSectionActions.Error(
                                it1
                            )
                        })
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
    }
}