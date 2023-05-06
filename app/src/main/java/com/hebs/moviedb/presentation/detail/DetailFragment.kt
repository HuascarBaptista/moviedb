package com.hebs.moviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.FragmentResourceDetailBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.domain.model.actions.DetailViewActions
import com.hebs.moviedb.presentation.base.BaseFragment
import com.hebs.moviedb.presentation.detail.items.DetailVideoMediaItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.loadImage
import com.hebs.moviedb.tools.openUrl
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment(), DetailVideoMediaItem.VideoMediaListener {

    private val resource: Resource by lazy {
        navArgs<DetailFragmentArgs>().value.resource
    }

    private val groupieAdapter = GroupieAdapter()
    private val detailViewModel: DetailViewModel by viewModels()

    private val binding by viewBinding {
        FragmentResourceDetailBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun getViewModel() = detailViewModel
    override fun getRefreshLayout() = binding.swipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewMedia.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupieAdapter
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        detailViewModel.videoMediaLiveData.observe(viewLifecycleOwner) {
            if (it is DetailViewActions.UpdateVideos) loadVideos(it.videos)
        }
        detailViewModel.loadMovies(resource)
    }

    private fun initView() {
        binding.apply {
            resource.coverImageUrl?.let {
                imageViewCover.loadImage(it)
            } ?: imageViewCover.hide()
            resource.posterImageUrl?.let { imageViewPoster.loadImage(it) }
            textViewTitle.text = resource.title
            if (resource.score > 0) {
                textViewRating.text =
                    requireContext().getString(
                        R.string.rating_range,
                        String.format("%.1f", resource.score)
                    )
            } else {
                textViewRating.hide()
                imageViewRating.hide()
            }
            textViewOverview.text = resource.overview

            swipeRefreshLayout.setOnRefreshListener {
                detailViewModel.loadMovies(resource)
            }
        }
    }

    private fun loadVideos(videos: List<VideoMedia>) {
        if (videos.isNotEmpty()) {
            initRecyclerView()
            val videoItems = videos.map {
                DetailVideoMediaItem(
                    it, this
                )
            }
            groupieAdapter.update(videoItems)
            binding.recyclerViewMedia.show()
        } else {
            binding.textViewTitleMedia.hide()
            binding.recyclerViewMedia.hide()
        }
    }

    override fun onVideoMediaSelected(youtubeUrl: String) {
        openUrl(youtubeUrl, requireContext())
    }
}