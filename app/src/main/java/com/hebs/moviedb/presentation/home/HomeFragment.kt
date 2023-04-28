package com.hebs.moviedb.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hebs.moviedb.databinding.FragmentHomeBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.presentation.detail.DetailListener
import com.hebs.moviedb.presentation.home.items.SectionHomeItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SectionHomeItem.ResourceSelectedListener {

    private var listener: DetailListener? = null

    private val binding by viewBinding {
        FragmentHomeBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    private val viewModel: HomeViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()
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
    }

    private fun initRecyclerView() {
        binding.recyclerViewHomeSection.adapter = groupieAdapter
    }

    private fun initViewModel() {
        viewModel.sectionsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeSectionActions.HideLoading -> binding.progressBarLoading.hide()
                is HomeSectionActions.UpdateSections -> updateSections(it.sections)
                is HomeSectionActions.Error -> showError(it.message)
            }
        }
        viewModel.loadMovies()
    }

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSections(sections: Set<ResourceSection>) {
        val sectionsItems = sections.map {
            SectionHomeItem(
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