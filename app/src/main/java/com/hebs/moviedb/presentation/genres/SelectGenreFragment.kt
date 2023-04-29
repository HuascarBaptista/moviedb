package com.hebs.moviedb.presentation.genres

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.databinding.FragmentSelectGenreBinding
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.base.BaseFragment
import com.hebs.moviedb.presentation.detail.GenreListener
import com.hebs.moviedb.presentation.genres.items.GenreTitleItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectGenreFragment : BaseFragment(), GenreTitleItem.GenreSelectedListener {

    private val genreViewModel: GenreViewModel by viewModels()
    private var listener: GenreListener? = null
    private val groupieAdapter = GroupieAdapter()

    private val binding by viewBinding {
        FragmentSelectGenreBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun getViewModel() = genreViewModel
    override fun getRefreshLayout() = binding.swipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? GenreListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewSectionGenres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupieAdapter
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        genreViewModel.genresLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GenreSectionActions.UpdateGenres -> updateGenres(it.genres)
                else -> {}
            }
        }
        genreViewModel.getGenres()
    }

    private fun updateGenres(genres: List<Genre>) {
        if (genres.isNotEmpty()) {
            binding.recyclerViewSectionGenres.show()
            binding.textViewDefaultOfflineMessage.hide()
            val genresItems = genres.map {
                GenreTitleItem(
                    it,
                    this
                )
            }
            groupieAdapter.addAll(genresItems)
        } else {
            binding.recyclerViewSectionGenres.hide()
            binding.textViewDefaultOfflineMessage.show()

        }
    }

    override fun onGenreSelected(genre: Genre) {
        binding.recyclerViewSectionGenres.scrollTo(0, 0)
        listener?.genreSelected(genre)
    }
}