package com.company.movieapp.data

import com.company.movieapp.R
import com.google.gson.annotations.SerializedName

data class Movies(
    val page: Page,
)

data class Page(
    val title: String,
    @SerializedName("total-content-items") val totalItems: String,
    @SerializedName("content-items") val contentItems: ContentItems,
)

data class ContentItems(
    val content: ArrayList<Movie>,
)

data class Movie(
    val name: String,
    @SerializedName("poster-image") val posterImage: String,
) {
    fun getMovieDrawable() = when (posterImage) {
        "poster1.jpg" -> R.drawable.poster1
        "poster2.jpg" -> R.drawable.poster2
        "poster3.jpg" -> R.drawable.poster3
        "poster4.jpg" -> R.drawable.poster4
        "poster5.jpg" -> R.drawable.poster5
        "poster6.jpg" -> R.drawable.poster6
        "poster7.jpg" -> R.drawable.poster7
        "poster8.jpg" -> R.drawable.poster8
        "poster9.jpg" -> R.drawable.poster9
        else -> R.drawable.ic_placeholder
    }
}