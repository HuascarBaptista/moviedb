package com.hebs.moviedb.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "video")
data class VideoMediaEntity(
    @PrimaryKey @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("published_at") val publishedAt: String,
    val resourceId: Int
)