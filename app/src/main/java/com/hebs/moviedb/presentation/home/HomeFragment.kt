package com.hebs.moviedb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hebs.moviedb.databinding.FragmentHomeBinding
import com.hebs.moviedb.tools.viewBinding

class HomeFragment : Fragment() {

    private val binding by viewBinding {
        FragmentHomeBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}