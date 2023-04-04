package com.company.movieapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecorator(private val spacing: Int, private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        when {
            // for the first item is the row
            position % spanCount == 0 -> {
                outRect.left = spacing
                outRect.right = spacing / 2
            }

            // for the last item is the row
            position % spanCount == spanCount - 1 -> {
                outRect.left = spacing / 2
                outRect.right = spacing
            }

            else -> {
                outRect.left = spacing / 2
                outRect.right = spacing / 2
            }
        }
    }
}