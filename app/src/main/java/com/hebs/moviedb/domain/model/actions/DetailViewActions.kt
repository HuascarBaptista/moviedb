package com.hebs.moviedb.domain.model.actions

import com.hebs.moviedb.domain.model.VideoMedia

sealed class DetailViewActions {
    data class UpdateVideos(val videos: List<VideoMedia>) : DetailViewActions()
    data class Error(val message: String) : DetailViewActions()
}
