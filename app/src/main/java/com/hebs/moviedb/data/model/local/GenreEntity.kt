package com.hebs.moviedb.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)