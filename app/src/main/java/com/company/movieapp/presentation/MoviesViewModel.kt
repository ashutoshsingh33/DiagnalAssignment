package com.company.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.movieapp.data.Movie
import com.company.movieapp.repositories.MoviesRepository
import com.company.movieapp.util.DataUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private var movies: MutableList<Movie> = mutableListOf()
    private val _state: MutableLiveData<List<Movie>> = MutableLiveData(movies)
    val state: LiveData<List<Movie>> = _state

    private val _headerTitle: MutableLiveData<String> = MutableLiveData()
    val headerTitle: LiveData<String> = _headerTitle

    var page: Int = 1
    var searchText = ""

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch() {
            val data = moviesRepository.getMovies(getFileName(page))
            movies.addAll(data.page.contentItems.content)
            _state.value = movies
            _headerTitle.value = data.page.title
        }
    }

    fun search(query: String) = viewModelScope.launch {
        if (query.isNotEmpty()) {
            val filteredMovie = movies.filter { it.name.lowercase().contains(query.lowercase()) }
            _state.value = filteredMovie
        } else {
            _state.value = movies
        }
    }

    private fun getFileName(page: Int) =
        when (page) {
            1 -> DataUtil.MOVIE_DATA_PAGE_ONE
            2 -> DataUtil.MOVIE_DATA_PAGE_TWO
            else -> DataUtil.MOVIE_DATA_PAGE_THREE
        }
}
