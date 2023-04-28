package com.hebs.moviedb.domain.model.actions

import com.hebs.moviedb.domain.model.ResourceSection

sealed class SearchSectionActions {
    object ShowLoading : SearchSectionActions()
    object HideLoading : SearchSectionActions()
    data class UpdateSearch(val searchResults: Set<ResourceSection>) : SearchSectionActions()
    data class Error(val message: String) : SearchSectionActions()
}
