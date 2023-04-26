package com.hebs.moviedb.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    override val id: Int,
    override val title: String?,
    override val overview: String?,
    override val rating: Double,
    override val posterImageURL: String?,
    override val coverImageURL: String?
) : Resource
