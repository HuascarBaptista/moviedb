package com.hebs.moviedb.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hebs.moviedb.databinding.FragmentHomeBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.presentation.base.BaseFragment
import com.hebs.moviedb.presentation.detail.DetailListener
import com.hebs.moviedb.presentation.home.items.CarouselResourceItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), CarouselResourceItem.ResourceSelectedListener {

    private var listener: DetailListener? = null

    private val binding by viewBinding {
        FragmentHomeBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    private val homeViewModel: HomeViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()
    override fun getViewModel() = homeViewModel
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
        initView()
    }

    private fun initView() {
        initRecyclerView()
        getRefreshLayout().setOnRefreshListener {
            homeViewModel.loadMovies()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewHomeSection.adapter = groupieAdapter
    }

    override fun initViewModel() {
        super.initViewModel()
        homeViewModel.sectionsLiveData.observe(viewLifecycleOwner) {
            if (it is HomeSectionActions.UpdateSections) updateSections(it.sections)
        }
        homeViewModel.loadMovies()
    }

    private fun updateSections(sections: Set<ResourceSection>) {
        if(sections.isNotEmpty()){
            val sectionsItems = sections.map {
                CarouselResourceItem(
                    it,
                    this
                )
            }
            groupieAdapter.update(sectionsItems)
            binding.textViewDefaultOfflineMessage.hide()
            binding.recyclerViewHomeSection.show()
        } else {
            binding.textViewDefaultOfflineMessage.show()
            binding.recyclerViewHomeSection.hide()
        }
    }

    override fun onItemSelected(resource: Resource) {
        listener?.showDetail(resource)
    }
}