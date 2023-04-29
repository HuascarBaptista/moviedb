package com.hebs.moviedb.presentation.detail.items

import android.view.View
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemVideoMediaBinding
import com.hebs.moviedb.domain.model.VideoMedia
import com.hebs.moviedb.tools.loadImage
import com.xwray.groupie.viewbinding.BindableItem

class DetailVideoMediaItem(
    private val videoMedia: VideoMedia, private val resourceSelectedListener: VideoMediaListener
) : BindableItem<ItemVideoMediaBinding>() {

    override fun getLayout() = R.layout.item_video_media

    override fun initializeViewBinding(view: View) = ItemVideoMediaBinding.bind(view)

    override fun bind(viewBinding: ItemVideoMediaBinding, position: Int) {
        viewBinding.root.setOnClickListener {
            videoMedia.youtubeURL.let {
                resourceSelectedListener.onVideoMediaSelected(it)
            }
        }
        videoMedia.imageURL.let {
            viewBinding.imageViewPreview.loadImage(it, false)
        }
    }

    interface VideoMediaListener {
        fun onVideoMediaSelected(youtubeUrl: String)
    }
}