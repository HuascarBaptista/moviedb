package com.hebs.moviedb.domain.model.actions

import com.hebs.moviedb.domain.model.ResourceSection

sealed class HomeSectionActions {
    data class Loading(val shouldShow: Boolean) : HomeSectionActions()
    data class UpdateSections(val sections: Set<ResourceSection>) : HomeSectionActions()
    data class Error(val message: String) : HomeSectionActions()
}
