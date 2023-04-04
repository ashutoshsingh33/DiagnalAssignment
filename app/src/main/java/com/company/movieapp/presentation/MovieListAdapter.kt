package com.company.movieapp.presentation

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.data.Movie
import com.company.movieapp.databinding.ItemMovieBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class MovieListAdapter(val lastItemReached: (Boolean) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback()) {

    private var searchText: String = ""

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Movie) {
            val spannableString = SpannableString(data.name)
            binding.name.text = data.name

            val nameInLower = data.name.lowercase()
            val searchInLower = searchText.lowercase()

            if (nameInLower.contains(searchInLower) && searchInLower.isNotEmpty()) {
                spannableString.setSpan(
                    ForegroundColorSpan(Color.RED),
                    nameInLower.indexOf(searchInLower),
                    nameInLower.indexOf(searchInLower) + searchText.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }

            binding.name.text = spannableString
            binding.thumbnail.setImageResource(data.getMovieDrawable())

            if (adapterPosition == itemCount - 1) {
                lastItemReached.invoke(true)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    fun setSearchText(query: String) {
        searchText = query
    }
}