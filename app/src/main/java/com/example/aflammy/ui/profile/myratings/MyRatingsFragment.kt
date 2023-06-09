package com.example.aflammy.ui.profile.myratings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aflammy.R
import com.example.aflammy.databinding.FragmentMyRatingsBinding
import com.example.aflammy.ui.base.BaseFragment
import com.example.aflammy.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyRatingsFragment : BaseFragment<FragmentMyRatingsBinding>() {
    override val fragmentLayoutId: Int = R.layout.fragment_my_ratings
    override val viewModel: MyRatingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getString(R.string.my_ratings))
        val adapter = RatedMoviesAdapter(emptyList(), viewModel)
        binding.recyclerViewRatedMovies.adapter = adapter
        collectLast(viewModel.myRatingUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: MyRatingUIEvent) {
        val action = when (event) {
            is MyRatingUIEvent.MovieEvent -> {
                MyRatingsFragmentDirections.actionRatedMoviesFragmentToMovieDetailFragment(event.movieID)
            }
            is MyRatingUIEvent.TVShowEvent -> {
                MyRatingsFragmentDirections.actionRatedMoviesFragmentToTvShowDetailsFragment(event.tvShowID)
            }
        }
        findNavController().navigate(action)
    }
}