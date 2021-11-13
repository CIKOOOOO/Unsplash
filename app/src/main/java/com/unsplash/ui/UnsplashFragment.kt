package com.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.unsplash.databinding.FragmentUnsplashBinding
import com.unsplash.model.Unsplash
import com.unsplash.navigation.Navigate
import com.unsplash.ui.detail.UnsplashDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UnsplashFragment : Fragment(), Navigate {

    private lateinit var binding: FragmentUnsplashBinding

    private val viewModel: UnsplashViewModel by viewModels()

    private lateinit var decorator: DividerItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnsplashBinding.inflate(inflater, container, false)

//        viewModel = ViewModelProvider(
//            requireActivity(),
//            Injection.provideViewModelFactory(context = requireActivity(), owner = this)
//        ).get(UnsplashViewModel::class.java)

        decorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvUnsplash.addItemDecoration(decorator)
        binding.rvUnsplash.layoutManager = LinearLayoutManager(requireContext())

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun FragmentUnsplashBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Unsplash>>,
        uiActions: (UiAction) -> Unit
    ) {
        val repoAdapter = UnsplashAdapter(this@UnsplashFragment)
        rvUnsplash.adapter = repoAdapter

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
//        val notLoading = repoAdapter.loadStateFlow
//            // Only emit when REFRESH LoadState for the paging source changes.
//            .distinctUntilChangedBy { it.source.refresh }
//            // Only react to cases where REFRESH completes i.e., NotLoading.
//            .map { it.source.refresh is LoadState.NotLoading }
//
//        val hasNotScrolledForCurrentSearch = uiState
//            .map { it.hasNotScrolledForCurrentSearch }
//            .distinctUntilChanged()

// ini buat error.
//        val shouldScrollToTop = combine(
//            notLoading,
//            hasNotScrolledForCurrentSearch,
//            Boolean::and
//        ).distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(repoAdapter::submitData)
        }

        lifecycleScope.launch {
            rvUnsplash.scrollToPosition(0)
//            shouldScrollToTop.collect { shouldScroll ->
//                if (shouldScroll) rvUnsplash.scrollToPosition(0)
//            }
        }

    }

    override fun navigate(unsplash: Unsplash) {
        val gson = Gson()
        val data = gson.toJson(unsplash)

        val myBundle = bundleOf(UnsplashDetailFragment.DATA to data)

        findNavController().navigate(R.id.unsplashDetailFragment, myBundle)
    }
}