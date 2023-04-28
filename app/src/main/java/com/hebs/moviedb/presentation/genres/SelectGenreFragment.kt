package com.hebs.moviedb.presentation.genres

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.databinding.FragmentSelectGenreBinding
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.detail.GenreListener
import com.hebs.moviedb.presentation.genres.items.GenreTitleItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectGenreFragment : Fragment(), GenreTitleItem.GenreSelectedListener {

    private val viewModel: GenreViewModel by viewModels()
    private var listener: GenreListener? = null
    private val groupieAdapter = GroupieAdapter()

    private val binding by viewBinding {
        FragmentSelectGenreBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

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

    private fun initViewModel() {
        viewModel.genresLiveData.observe(viewLifecycleOwner) {
                        when (it) {
                            is GenreSectionActions.HideLoading -> binding.progressBarLoading.hide()
                            is GenreSectionActions.UpdateGenres -> updateGenres(it.genres)
                            is GenreSectionActions.Error -> showError(it.message)
                            else -> {}
                        }
        }
        viewModel.getGenres()
    }

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateGenres(genres: List<Genre>) {
        val genresItems = genres.map {
            GenreTitleItem(
                it,
                this
            )
        }
        groupieAdapter.addAll(genresItems)
    }

    override fun onGenreSelected(genre: Genre) {
        listener?.genreSelected(genre)
    }
}