package com.hebs.moviedb.presentation.home.items

import android.view.View
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemHomeSectionBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class SectionHomeItem(
    private val resourceSection: ResourceSection,
    private val resourceSelectedListener: ResourceSelectedListener
) : BindableItem<ItemHomeSectionBinding>() {

    private val adapter = GroupieAdapter()

    override fun getLayout() = R.layout.item_home_section

    override fun initializeViewBinding(view: View) =
        ItemHomeSectionBinding.bind(view)

    override fun bind(viewBinding: ItemHomeSectionBinding, position: Int) {
        viewBinding.textViewSectionTitle.text = resourceSection.categoryName
        viewBinding.recyclerViewSectionVideos.adapter = adapter
        adapter.update(mapResourcesItems(resourceSection.resources))
    }

    private fun mapResourcesItems(resources: List<Resource>): List<SectionVideoItem> {
        return resources.map {
            SectionVideoItem(
                it,
                resourceSelectedListener
            )
        }
    }

    interface ResourceSelectedListener {
        fun onItemSelected(id: Int)
    }
}