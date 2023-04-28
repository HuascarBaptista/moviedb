package com.hebs.moviedb.domain.model.actions

import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.ResourceSection

sealed class GenreSectionActions {
    object HideLoading : GenreSectionActions()
    data class UpdateGenres(val genres: List<Genre>) : GenreSectionActions()
    data class UpdateSections(val sections: Set<ResourceSection>) : GenreSectionActions()
    data class Error(val message: String) : GenreSectionActions()
}
