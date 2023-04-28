package com.hebs.moviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    override val id: Int,
    override val title: String,
    override val overview: String?,
    override val rating: Double,
    override val score: Double,
    override val posterImageUrl: String?,
    override val coverImageUrl: String?
) : Resource, Parcelable
