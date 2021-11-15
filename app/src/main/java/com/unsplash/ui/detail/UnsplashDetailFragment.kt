package com.unsplash.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.unsplash.R
import com.unsplash.databinding.FragmentUnsplashBinding
import com.unsplash.databinding.FragmentUnsplashDetailBinding
import com.unsplash.model.Unsplash
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsplashDetailFragment : Fragment() {

    private lateinit var binding: FragmentUnsplashDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnsplashDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stringData = arguments?.getString(DATA)
        val gson = Gson()

        val unsplash: Unsplash = gson.fromJson(stringData, Unsplash::class.java)

        binding.apply {
            Picasso.get()
                .load(unsplash.picture)
                .into(ivDetail)

            tvNameDetail.text = unsplash.name
            tvDescriptionDetail.text = unsplash.description
        }
    }

    companion object{
        val DATA = "data"
    }
}
