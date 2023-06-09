package com.hebs.moviedb.presentation.home.items

import android.view.View
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemSearchSectionVideoBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.tools.animateItem
import com.hebs.moviedb.tools.loadImage
import com.xwray.groupie.viewbinding.BindableItem

class SearchSectionVideoItem(
    private val resource: Resource,
    private val sectionSelectedListener: CarouselResourceItem.ResourceSelectedListener
) : BindableItem<ItemSearchSectionVideoBinding>() {

    override fun getLayout() = R.layout.item_search_section_video

    override fun initializeViewBinding(view: View) = ItemSearchSectionVideoBinding.bind(view)

    override fun bind(viewBinding: ItemSearchSectionVideoBinding, position: Int) {
        viewBinding.animateItem()

        viewBinding.textViewVideoTitle.text = resource.title
        viewBinding.root.setOnClickListener {
            resource.id.let {
                sectionSelectedListener.onItemSelected(resource)
            }
        }
        resource.posterImageUrl?.let {
            viewBinding.imageViewVideoPoster.loadImage(it)
        }
    }
}