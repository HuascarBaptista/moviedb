package com.hebs.moviedb.data.mappers

import com.hebs.moviedb.data.model.local.SectionEntityType
import com.hebs.moviedb.domain.model.SectionType
import javax.inject.Inject

class CategoryTypeMapper @Inject constructor() {

    fun mapToEntity(categoryType: SectionType): SectionEntityType {
        return when (categoryType) {
            SectionType.POPULAR_MOVIES -> SectionEntityType.POPULAR_MOVIES
            SectionType.POPULAR_TV_SHOWS -> SectionEntityType.POPULAR_TV_SHOWS
            SectionType.TOP_RATED_MOVIES -> SectionEntityType.TOP_RATED_MOVIES
            SectionType.TOP_RATED_TV_SHOWS -> SectionEntityType.TOP_RATED_TV_SHOWS
            SectionType.SEARCH_MOVIES -> SectionEntityType.SEARCH_MOVIES
            SectionType.SEARCH_TV_SHOWS -> SectionEntityType.SEARCH_TV_SHOWS
        }
    }

    fun mapFromEntity(categoryType: SectionEntityType) =
        when (categoryType) {
            SectionEntityType.POPULAR_MOVIES -> SectionType.POPULAR_MOVIES
            SectionEntityType.POPULAR_TV_SHOWS -> SectionType.POPULAR_TV_SHOWS
            SectionEntityType.TOP_RATED_MOVIES -> SectionType.TOP_RATED_MOVIES
            SectionEntityType.TOP_RATED_TV_SHOWS -> SectionType.TOP_RATED_TV_SHOWS
            SectionEntityType.SEARCH_MOVIES -> SectionType.SEARCH_MOVIES
            SectionEntityType.SEARCH_TV_SHOWS -> SectionType.SEARCH_TV_SHOWS
        }
}