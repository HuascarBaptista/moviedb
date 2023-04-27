package com.hebs.moviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hebs.moviedb.databinding.FragmentHomeBinding
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.HomeSectionActions
import com.hebs.moviedb.presentation.home.items.SectionHomeItem
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SectionHomeItem.ResourceSelectedListener {

    private val binding by viewBinding {
        FragmentHomeBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = GroupieAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerViewHomeSection.adapter = adapter
    }

    private fun initViewModel() {
        viewModel.sectionsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeSectionActions.Loading -> showLoading(it.shouldShow)
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

    private fun updateSections(sections: List<ResourceSection>) {
        val sectionsItems = sections.map {
            SectionHomeItem(
                it,
                this
            )
        }
        adapter.update(sectionsItems)
    }

    private fun showLoading(shouldShow: Boolean) {
        Toast.makeText(context, shouldShow.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(id: Int) {
        TODO("Not yet implemented")
    }
}