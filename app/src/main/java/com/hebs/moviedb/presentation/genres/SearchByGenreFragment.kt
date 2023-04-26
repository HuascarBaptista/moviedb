package com.hebs.moviedb.presentation.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hebs.moviedb.databinding.FragmentSearchByGenreBinding
import com.hebs.moviedb.tools.viewBinding

class SearchByGenreFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSearchByGenreBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}