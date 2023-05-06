package com.hebs.moviedb.domain.model.actions

import com.hebs.moviedb.domain.model.ResourceSection

sealed class SearchSectionActions {
    data class UpdateSearch(val searchResults: Set<ResourceSection>) : SearchSectionActions()
}
