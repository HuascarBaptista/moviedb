package com.hebs.moviedb.domain.model

data class VideoMedia(
    val id: String,
    val key: String,
    val name: String,
    val publishedAt: String
) {
    val imageURL: String get() = IMAGE_YOUTUBE_URL.replaceKeyIntoString(key)
    val youtubeURL: String get() = VIDEO_YOUTUBE_URL.replaceKeyIntoString(key)

    private fun String.replaceKeyIntoString(key: String) =
        this.replace(KEY_TO_REPLACE, key, ignoreCase = true)

    private companion object {
        const val IMAGE_YOUTUBE_URL = "https://img.youtube.com/vi/RESOURCE_KEY/hqdefault.jpg"
        const val VIDEO_YOUTUBE_URL = "https://www.youtube.com/watch?v=RESOURCE_KEY"
        const val KEY_TO_REPLACE = "RESOURCE_KEY"
    }
}
