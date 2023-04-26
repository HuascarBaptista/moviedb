package com.hebs.moviedb.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hebs.moviedb.databinding.FragmentSearchBinding
import com.hebs.moviedb.tools.viewBinding

class SearchFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSearchBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}