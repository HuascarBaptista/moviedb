package com.hebs.moviedb.domain.model

data class ResourceCategory(
    val category: Category,
    val resources: List<Resource>,
)

enum class Category{
    POPULAR, TOP_RATED
}