package com.company.movieapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.company.movieapp.R
import com.company.movieapp.databinding.ActivityMoviesBinding
import com.company.movieapp.util.GridItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setSupportActionBar(binding.header.toolbar)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(this) {
            adapter.setSearchText(viewModel.searchText)
            adapter.submitList(it.toMutableList())
        }

        viewModel.headerTitle.observe(this) {
            supportActionBar?.title = it
        }
    }

    private fun setAdapter() {
        adapter = MovieListAdapter {
            if (it && viewModel.page < 3 && viewModel.searchText.isEmpty()) {
                viewModel.page = viewModel.page + 1
                viewModel.loadMovies()
            }
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.gallery_columns))
        binding.recyclerView.addItemDecoration(GridItemDecorator(40, resources.getInteger(R.integer.gallery_columns)))
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        if (viewModel.searchText.isNotEmpty()) {
            menu.findItem(R.id.action_search)?.expandActionView()
            searchView.setQuery(viewModel.searchText, false)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchText = it
                    viewModel.search(it)
                    if (it.isNotEmpty() && it.length > 2) {
                        adapter.setSearchText(it)
                    }
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}