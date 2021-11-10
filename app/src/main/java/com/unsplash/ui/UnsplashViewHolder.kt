package com.unsplash.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unsplash.R
import com.unsplash.model.Unsplash

class UnsplashViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.tv_name)
    private val image: ImageView = view.findViewById(R.id.iv_unsplash)

    private var unsplash: Unsplash? = null

    init {
        view.setOnClickListener {
            Toast.makeText(view.context, "piuw", Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(unsplash: Unsplash?) {
        if (unsplash == null) {
            name.text = "Empty data"
            Log.e("asd", "empty")
//            Toast.makeText(context, "piuw", Toast.LENGTH_SHORT).show()
        } else {
            showUnsplashData(unsplash)
        }
    }

    private fun showUnsplashData(unsplash: Unsplash) {
        this.unsplash = unsplash
        name.text = unsplash.name
        Picasso.get()
            .load(unsplash.picture)
            .into(image)
    }

    companion object {
        fun create(parent: ViewGroup): UnsplashViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_unsplash, parent, false)
            return UnsplashViewHolder(view)
        }
    }
}