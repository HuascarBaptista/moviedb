package com.hebs.moviedb.presentation.home.items

import android.view.View
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemSectionVideoBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.tools.animateItem
import com.hebs.moviedb.tools.loadImage
import com.xwray.groupie.viewbinding.BindableItem

class SectionVideoItem(
    private val resource: Resource,
    private val sectionSelectedListener: CarouselResourceItem.ResourceSelectedListener
) : BindableItem<ItemSectionVideoBinding>() {

    override fun getLayout() = R.layout.item_section_video

    override fun initializeViewBinding(view: View) =
        ItemSectionVideoBinding.bind(view)

    override fun bind(viewBinding: ItemSectionVideoBinding, position: Int) {
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