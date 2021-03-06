package com.unsplash.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.unsplash.databinding.FragmentUnsplashDetailBinding
import com.unsplash.model.Unsplash
import com.unsplash.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsplashDetailFragment : BaseFragment() {

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

        binding.toolbar.ivLogout.visibility = View.GONE

        binding.apply {
            Picasso.get()
                .load(unsplash.picture)
                .into(ivDetail)

            tvNameDetail.text = unsplash.name
            tvDescriptionDetail.text = unsplash.description

            tvThumbs.text = "${unsplash.likes}"
            tvInstagramUserDetail.text = "User: ${unsplash.twitterUsername}"
            tvTotalPhotos.text = "Total Photos: ${unsplash.totalPhotos}"
        }
    }

    companion object {
        val DATA = "data"
    }

//    override fun onResume() {
//        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//        (activity as MainActivity?)?.cancelSessionTimer()
//        (activity as MainActivity?)?.startSessionTimer()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//        (activity as MainActivity?)?.cancelSessionTimer()
//    }
//
//    override fun onUserInteraction() {
//        (activity as MainActivity?)?.cancelSessionTimer()
//        (activity as MainActivity?)?.startSessionTimer()
//    }
}
