package com.hebs.moviedb.presentation.genres

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hebs.moviedb.databinding.FragmentSearchByGenreBinding
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.detail.DetailListener
import com.hebs.moviedb.presentation.home.items.CarouselResourceItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByGenreFragment : Fragment(), CarouselResourceItem.ResourceSelectedListener {

    private var listener: DetailListener? = null

    private val genre: Genre by lazy {
        navArgs<SearchByGenreFragmentArgs>().value.genre
    }

    private val binding by viewBinding {
        FragmentSearchByGenreBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    private val viewModel: GenreViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? DetailListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewByGenreResource.adapter = groupieAdapter
    }

    private fun initViewModel() {
        viewModel.genresLiveData.observe(viewLifecycleOwner) {
                        when (it) {
                            is GenreSectionActions.HideLoading -> binding.progressBarLoading.hide()
                            is GenreSectionActions.UpdateSections -> updateSections(it.sections)
                            is GenreSectionActions.Error -> showError(it.message)
                            else -> {}
                        }
        }
        viewModel.getByGenre(genre)
    }

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSections(sections: Set<ResourceSection>) {
        val sectionsItems = sections.map {
            CarouselResourceItem(
                it,
                this
            )
        }
        groupieAdapter.update(sectionsItems)
    }

    override fun onItemSelected(resource: Resource) {
        listener?.showDetail(resource)
    }
}