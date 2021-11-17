package com.unsplash.ui

import androidx.fragment.app.Fragment
import com.unsplash.MainActivity
import com.unsplash.utils.UserInteractionListener

open class BaseFragment : Fragment(), UserInteractionListener {

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.cancelSessionTimer()
        (activity as MainActivity?)?.startSessionTimer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity?)?.cancelSessionTimer()
    }


    override fun onUserInteraction() {
        (activity as MainActivity?)?.cancelSessionTimer()
        (activity as MainActivity?)?.startSessionTimer()
    }

}