package com.example.aflammy.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aflammy.R
import com.example.aflammy.databinding.FragmentCategoryBinding
import com.example.aflammy.ui.adapters.LoadUIStateAdapter
import com.example.aflammy.ui.base.BaseFragment
import com.example.aflammy.ui.category.uiState.CategoryUIEvent
import com.example.aflammy.utilities.Constants.TV_CATEGORIES_ID
import com.example.aflammy.utilities.collect
import com.example.aflammy.utilities.collectLast
import com.example.aflammy.utilities.setSpanSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override val fragmentLayoutId = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels()
    private val allMediaAdapter by lazy { CategoryAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getTitle())
        setMediaAdapter()
        collectEvent()
        collectData()
    }

    private fun setMediaAdapter() {
        val footerAdapter = LoadUIStateAdapter(allMediaAdapter::retry)
        binding.recyclerMedia.adapter = allMediaAdapter.withLoadStateFooter(footerAdapter)

        val mManager = binding.recyclerMedia.layoutManager as GridLayoutManager
        mManager.setSpanSize(footerAdapter, allMediaAdapter, mManager.spanCount)

        collect(flow = allMediaAdapter.loadStateFlow,
            action = { viewModel.setErrorUiState(it) })
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                collectLast(viewModel.uiState.value.media)
                { allMediaAdapter.submitData(it) }
            }
        }
    }

    private fun collectEvent() {
        collect(viewModel.categoryUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: CategoryUIEvent) {
        when (event) {
            is CategoryUIEvent.ClickMovieEvent -> {
                if (viewModel.args.mediaId == TV_CATEGORIES_ID) {
                    navigateToTvShowDetails(event.movieID)
                } else {
                    navigateToMovieDetails(event.movieID)
                }
            }
            CategoryUIEvent.RetryEvent -> {
                allMediaAdapter.retry()
            }
            is CategoryUIEvent.SelectedCategory -> {
                viewModel.getMediaList(event.categoryID)
            }
        }
    }

    private fun navigateToMovieDetails(movieId: Int) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun navigateToTvShowDetails(tvShowId: Int) {
        val action =
            CategoryFragmentDirections.actionCategoryFragmentToTvShowDetailsFragment(tvShowId)
        findNavController().navigate(action)
    }

    private fun getTitle(): String {
        return if (viewModel.args.mediaId == TV_CATEGORIES_ID) {
            resources.getString(R.string.title_tv_shows)
        } else {
            resources.getString(R.string.movies)
        }
    }
}
