package com.hebs.moviedb.presentation.genres

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hebs.moviedb.databinding.FragmentSearchByGenreBinding
import com.hebs.moviedb.domain.model.Genre
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.GenreSectionActions
import com.hebs.moviedb.presentation.base.BaseFragment
import com.hebs.moviedb.presentation.detail.DetailListener
import com.hebs.moviedb.presentation.home.items.CarouselResourceItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByGenreFragment : BaseFragment(), CarouselResourceItem.ResourceSelectedListener {

    private var listener: DetailListener? = null

    private val genre: Genre by lazy {
        navArgs<SearchByGenreFragmentArgs>().value.genre
    }

    private val genreViewModel: GenreViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()

    private val binding by viewBinding {
        FragmentSearchByGenreBinding.inflate(
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
        listener = context as? DetailListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        initView()
    }

    private fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            genreViewModel.getByGenre(genre)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewByGenreResource.adapter = groupieAdapter
    }

    override fun initViewModel() {
        super.initViewModel()
        genreViewModel.genresLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GenreSectionActions.UpdateSections -> updateSections(it.sections)
                else -> {}
            }
        }
        genreViewModel.getByGenre(genre)
    }

    private fun updateSections(sections: Set<ResourceSection>) {
        if (sections.isNotEmpty()) {
            binding.recyclerViewByGenreResource.show()
            binding.textViewDefaultOfflineMessage.hide()

            val sectionsItems = sections.map {
                CarouselResourceItem(
                    it,
                    this
                )
            }
            groupieAdapter.update(sectionsItems)
        } else {
            binding.recyclerViewByGenreResource.hide()
            binding.textViewDefaultOfflineMessage.show()
        }
    }

    override fun onItemSelected(resource: Resource) {
        listener?.showDetail(resource)
    }
}