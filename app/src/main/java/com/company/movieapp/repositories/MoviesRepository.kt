package com.company.movieapp.repositories

import com.company.movieapp.data.Movies
import com.company.movieapp.util.DataUtil
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val dataUtil: DataUtil) {

    fun getMovies(fileName: String): Movies {
        return dataUtil.loadMovies(fileName)
    }
}