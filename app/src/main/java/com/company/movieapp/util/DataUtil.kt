package com.company.movieapp.util

import android.content.Context
import com.company.movieapp.data.Movies
import com.google.gson.Gson

class DataUtil(private val context: Context) {

    companion object {
        const val MOVIE_DATA_PAGE_ONE ="content_listing_page_one.json"
        const val MOVIE_DATA_PAGE_TWO ="content_listing_page_two.json"
        const val MOVIE_DATA_PAGE_THREE ="content_listing_page_three.json"
    }

    fun loadMovies(fileName: String): Movies {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText().trim() }
        return Gson().fromJson(jsonString, Movies::class.java)
    }
}