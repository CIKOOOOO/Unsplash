package com.unsplash.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.unsplash.R
import com.unsplash.MainActivity
import com.unsplash.databinding.ActivityMainBinding
import com.unsplash.databinding.FragmentUnsplashBinding
import com.unsplash.model.Unsplash
import com.unsplash.navigation.Navigate
import com.unsplash.ui.detail.UnsplashDetailFragment
import com.unsplash.utils.DialogUtil
import com.unsplash.utils.SharedPreferenceUtil
import com.unsplash.utils.UserInteractionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UnsplashFragment : BaseFragment(), Navigate {

    private lateinit var binding: FragmentUnsplashBinding

    private val viewModel: UnsplashViewModel by viewModels()
    private lateinit var ivLogout: ImageView

    @Inject
    lateinit var sharedPref: SharedPreferenceUtil

    private lateinit var decorator: DividerItemDecoration

    private var isSuccessClearSharedPref = true

    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                logout()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnsplashBinding.inflate(inflater, container, false)

        inituserInteraction()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        decorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvUnsplash.addItemDecoration(decorator)
        binding.rvUnsplash.layoutManager = LinearLayoutManager(requireContext())
        binding.toolbar.ivLogout.setOnClickListener {
            logout()
        }

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )

        return binding.root
    }

    private fun inituserInteraction() {
        (activity as MainActivity?)?.setUInteractionListener(this)
    }

    private fun logout() {
        try {
            showLogoutDialog()
        } catch (e: Exception) {
            generalErrorDialog()
        }
    }

    private fun showLogoutDialog() {
        (activity as MainActivity?)?.cancelSessionTimer()
        DialogUtil().showTwoButtonDialog(requireActivity(), "Are you sure want to logout?",
            "No", { (activity as MainActivity?)?.startSessionTimer() }, "Yes", {
                sharedPref.clearPref()
                findNavController().popBackStack(R.id.loginFragment, false)
            })
    }

    private fun generalErrorDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setTitle("Error")
            setMessage("Oops something went wrong, please try again")
            setPositiveButton("Yes") { dialog, whichButton ->
                dialog.dismiss()
            }
        }.create().show()
    }

    private fun FragmentUnsplashBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Unsplash>>,
        uiActions: (UiAction) -> Unit
    ) {
        val repoAdapter = UnsplashAdapter(this@UnsplashFragment)
        rvUnsplash.adapter = repoAdapter

        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        bindList(
            repoAdapter = repoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentUnsplashBinding.bindList(
        repoAdapter: UnsplashAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Unsplash>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        rvUnsplash.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })
//
        val notLoading = repoAdapter.loadStateFlow
            // Only emit when REFRESH LoadState for the paging source changes.
            .distinctUntilChangedBy { it.source.refresh }
            // Only react to cases where REFRESH completes i.e., NotLoading.
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(repoAdapter::submitData)
        }

        lifecycleScope.launch {
            rvUnsplash.scrollToPosition(0)
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) rvUnsplash.scrollToPosition(0)
            }
        }

    }

    private fun FragmentUnsplashBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        binding.searchBar.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        binding.searchBar.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(binding.searchBar.etSearch::setText)
        }
    }

    private fun FragmentUnsplashBinding.updateRepoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        binding.searchBar.etSearch.text.trim().let {
            if (it.isNotEmpty()) {
                rvUnsplash.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    override fun navigate(unsplash: Unsplash) {
        val gson = Gson()
        val data = gson.toJson(unsplash)

        val myBundle = bundleOf(UnsplashDetailFragment.DATA to data)

        findNavController().navigate(R.id.unsplashDetailFragment, myBundle)
    }

}