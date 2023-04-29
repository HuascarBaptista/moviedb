package com.hebs.moviedb.presentation.genres.items

import android.view.View
import android.view.animation.AnimationUtils
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.ItemSelectGenreTitleBinding
import com.hebs.moviedb.domain.model.Genre
import com.xwray.groupie.viewbinding.BindableItem

class GenreTitleItem(
    private val genre: Genre,
    private val genreSelectedListener: GenreSelectedListener
) : BindableItem<ItemSelectGenreTitleBinding>() {

    override fun getLayout() = R.layout.item_select_genre_title

    override fun initializeViewBinding(view: View) =
        ItemSelectGenreTitleBinding.bind(view)

    override fun bind(viewBinding: ItemSelectGenreTitleBinding, position: Int) {
        val fadeInAnimation = AnimationUtils.loadAnimation(viewBinding.root.context, R.anim.fade_in)
        viewBinding.root.startAnimation(fadeInAnimation)
        viewBinding.textViewGenreTitle.text = genre.name
        viewBinding.root.setOnClickListener {
            genre.id.let {
                genreSelectedListener.onGenreSelected(genre)
            }
        }
    }

    interface GenreSelectedListener {
        fun onGenreSelected(genre: Genre)
    }
}