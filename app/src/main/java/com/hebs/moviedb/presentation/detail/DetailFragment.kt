package com.hebs.moviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.databinding.FragmentResourceDetailBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.domain.model.actions.DetailViewActions
import com.hebs.moviedb.presentation.detail.items.DetailVideoMediaItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.loadImage
import com.hebs.moviedb.tools.openUrl
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), DetailVideoMediaItem.VideoMediaListener {

    private val resource: Resource by lazy {
        navArgs<DetailFragmentArgs>().value.resource
    }

    private val groupieAdapter = GroupieAdapter()
    private val viewModel: DetailViewModel by viewModels()

    private val binding by viewBinding {
        FragmentResourceDetailBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewMedia.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = groupieAdapter
            isNestedScrollingEnabled = true
        }
    }

    private fun initViewModel() {
        viewModel.videoMediaLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewActions.UpdateVideos -> loadVideos(it.videos)
                is DetailViewActions.Error -> showError(it.message)
            }
        }
        viewModel.loadMovies(resource)
    }

    private fun initView() {
        binding.apply {
            resource.coverImageUrl?.let { imageViewCover.loadImage(it) } ?: imageViewCover.hide()
            resource.posterImageUrl?.let { imageViewPoster.loadImage(it) }
            textViewTitle.text = resource.title
            if (resource.score > 0) {
                textViewRating.text = resource.score.toString()
            } else {
                textViewRating.hide()
                imageViewRating.hide()
            }
            textViewOverview.text = resource.overview
        }
    }

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadVideos(videos: List<VideoMedia>) {
        if (videos.isNotEmpty()) {
            val videoItems = videos.map {
                DetailVideoMediaItem(
                    it,
                    this
                )
            }
            groupieAdapter.update(videoItems)
            binding.recyclerViewMedia.show()
            initRecyclerView()
        } else {
            binding.textViewTitleMedia.hide()
            binding.recyclerViewMedia.hide()
        }
    }

    override fun onVideoMediaSelected(youtubeUrl: String) {
        openUrl(youtubeUrl, requireContext())
    }
}