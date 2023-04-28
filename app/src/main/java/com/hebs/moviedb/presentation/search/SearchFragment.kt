package com.hebs.moviedb.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.FragmentSearchBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.SearchSectionActions
import com.hebs.moviedb.presentation.detail.DetailListener
import com.hebs.moviedb.presentation.home.items.CarouselResourceItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), CarouselResourceItem.ResourceSelectedListener {

    private var listener: DetailListener? = null

    private val viewModel: SearchViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()

    private val binding by viewBinding {
        FragmentSearchBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextListener()
        initViewModel()
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? DetailListener
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearchResource.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupieAdapter
        }
    }

    private fun initViewModel() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
            Log.e("hebshebs", " Nuevo Evento $it")
            when (it) {
                is SearchSectionActions.ShowLoading -> binding.progressBarLoading.show()
                is SearchSectionActions.HideLoading -> binding.progressBarLoading.hide()
                is SearchSectionActions.UpdateSearch -> updateSearch(it.searchResults)
                is SearchSectionActions.Error -> showError(it.message)
            }
        }
    }

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSearch(search: Set<ResourceSection>) {
        val searchItems = search.map {
            Log.e("hebshebs", " Nuevo Item " + it.categoryName)
            Log.e("hebshebs", " Tama;o recursos " + it.resources.size)
            CarouselResourceItem(
                it,
                this,
                true
            )
        }
        Log.e("hebshebs", " Agregar $searchItems")
        groupieAdapter.update(searchItems)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTextListener() {
        binding.editTextSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(currentText: Editable) {
                updateIcon(currentText.isNotEmpty())
                updateQuery(currentText.toString())
            }
        })

        binding.editTextSearchQuery.setOnTouchListener { _, event ->
            if (isTappingTextDrawable(event) && binding.editTextSearchQuery.text?.isNotEmpty() == true) {
                binding.editTextSearchQuery.setText("")
                updateIcon(false)
                binding.editTextSearchQuery.performClick()
                return@setOnTouchListener true
            }
            false
        }
    }

    private fun updateQuery(query: String) {
        viewModel.search(query = query)
    }

    private fun isTappingTextDrawable(event: MotionEvent) =
        event.action == MotionEvent.ACTION_UP && event.rawX >= (binding.editTextSearchQuery.right - binding.editTextSearchQuery.compoundDrawables[2].bounds.width())

    private fun updateIcon(hasText: Boolean) {
        val iconResource = if (hasText) R.drawable.ic_clear else R.drawable.ic_search
        AppCompatResources.getDrawable(requireContext(), iconResource)?.let { icon ->
            icon.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
            binding.editTextSearchQuery.setCompoundDrawables(null, null, icon, null)
        }
    }

    override fun onItemSelected(resource: Resource) {
        listener?.showDetail(resource)
    }
}