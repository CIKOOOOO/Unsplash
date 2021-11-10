package com.unsplash.ui

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.unsplash.model.Unsplash

class UnsplashAdapter : PagingDataAdapter<Unsplash, UnsplashViewHolder>(UNSPLASH_COMPARATOR) {

    companion object {
        private val UNSPLASH_COMPARATOR = object : DiffUtil.ItemCallback<Unsplash>() {
            override fun areItemsTheSame(oldItem: Unsplash, newItem: Unsplash): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Unsplash, newItem: Unsplash): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: UnsplashViewHolder, position: Int) {
        val unsplashItem = getItem(position)
        Log.e("asd", "${position}")
        if (unsplashItem != null)
            holder.bind(unsplashItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashViewHolder =
        UnsplashViewHolder.create(parent)
}