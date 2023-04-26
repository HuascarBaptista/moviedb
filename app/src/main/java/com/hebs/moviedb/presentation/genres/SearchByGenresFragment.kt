package com.hebs.moviedb.presentation.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hebs.moviedb.databinding.FragmentSearchByGenresBinding
import com.hebs.moviedb.tools.viewBinding

class SearchByGenresFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSearchByGenresBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}