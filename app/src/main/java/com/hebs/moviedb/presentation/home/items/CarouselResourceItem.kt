package com.hebs.moviedb.presentation.home.items

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemCarouselSectionResourcesBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class CarouselResourceItem(
    private val resourceSection: ResourceSection,
    private val resourceSelectedListener: ResourceSelectedListener,
    private val shouldShowResourcesInGrid: Boolean = false
) : BindableItem<ItemCarouselSectionResourcesBinding>() {

    private val groupieAdapter = GroupieAdapter()

    override fun getLayout() = R.layout.item_carousel_section_resources

    override fun initializeViewBinding(view: View) =
        ItemCarouselSectionResourcesBinding.bind(view)

    override fun bind(viewBinding: ItemCarouselSectionResourcesBinding, position: Int) {
        initRecycler(viewBinding)
        Log.e("hebshebs", " New bind " + resourceSection.categoryName)
        viewBinding.textViewSectionTitle.text = resourceSection.categoryName
        groupieAdapter.update(mapResourcesItems(resourceSection.resources))
    }

    private fun initRecycler(viewBinding: ItemCarouselSectionResourcesBinding) {
        if (shouldShowResourcesInGrid) {
            viewBinding.recyclerViewSectionVideos.layoutManager =
                GridLayoutManager(viewBinding.root.context, 2)
        }
        viewBinding.recyclerViewSectionVideos.adapter = groupieAdapter
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
        fun onItemSelected(resource: Resource)
    }
}