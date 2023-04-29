package com.hebs.moviedb.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hebs.moviedb.R
import com.hebs.moviedb.databinding.FragmentSearchBinding
import com.hebs.moviedb.domain.model.Resource
import com.hebs.moviedb.domain.model.ResourceSection
import com.hebs.moviedb.domain.model.actions.SearchSectionActions
import com.hebs.moviedb.presentation.base.BaseFragment
import com.hebs.moviedb.presentation.home.items.CarouselResourceItem
import com.hebs.moviedb.tools.hide
import com.hebs.moviedb.tools.hideKeyboard
import com.hebs.moviedb.tools.show
import com.hebs.moviedb.tools.showKeyboard
import com.hebs.moviedb.tools.viewBinding
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment(), CarouselResourceItem.ResourceSelectedListener {

    private var lastQuery: String = ""

    private val searchViewModel: SearchViewModel by viewModels()

    private val groupieAdapter = GroupieAdapter()

    private val binding by viewBinding {
        FragmentSearchBinding.inflate(
            LayoutInflater.from(requireContext())
        )
    }

    private val handler = Handler(Looper.getMainLooper())

    private val updateQueryRunnable = Runnable { searchViewModel.search(lastQuery) }
    override fun getViewModel() = searchViewModel
    override fun getRefreshLayout() = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun initView() {
        initTextListener()
        initRecyclerView()
        requestFocusOpenKeyboard()
    }

    private fun requestFocusOpenKeyboard() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.editTextSearchQuery.requestFocus()
            }
        }.also {
            showKeyboard()
        })
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearchResource.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupieAdapter
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        searchViewModel.searchLiveData.observe(viewLifecycleOwner) {
            if (it is SearchSectionActions.UpdateSearch) updateSearchResults(it.searchResults)
        }
    }

    private fun updateSearchResults(search: Set<ResourceSection>) {
        if (search.isNotEmpty()) {
            binding.recyclerViewSearchResource.show()
            val searchItems = search.map {
                CarouselResourceItem(
                    it, this, true
                )
            }
            groupieAdapter.update(searchItems)
            binding.textViewDefaultOfflineMessage.hide()
        } else {
            binding.textViewDefaultOfflineMessage.show()
            binding.recyclerViewSearchResource.hide()
        }
    }

    private fun initTextListener() {
        binding.editTextSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(currentText: Editable) {
                updateIcon(currentText.isNotEmpty())
                updateQuery(currentText.toString())
            }
        })
        binding.editTextSearchQuery.setOnEditorActionListener { _, _, _ ->
            binding.editTextSearchQuery.clearFocus()
            hideKeyboard()
            true
        }
        removeQueryWhenTapped()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun removeQueryWhenTapped() {
        binding.editTextSearchQuery.setOnTouchListener { _, event ->
            if (isTappingTextDrawable(event) && binding.editTextSearchQuery.text?.isNotEmpty() == true) {
                binding.editTextSearchQuery.setText("")
                updateIcon(false)
                updateQuery("")
                return@setOnTouchListener true
            }
            false
        }
    }

    private fun initSearchQueryAction() {
        binding.editTextSearchQuery.performClick()
        requestFocusOpenKeyboard()
        binding.recyclerViewSearchResource.hide()
    }

    private fun updateQuery(query: String) {
        if (isSameLastQuery(query)) {
            lastQuery = query.trim()
            if (query.isBlank()) {
                initSearchQueryAction()
            } else {
                handler.removeCallbacks(updateQueryRunnable)
                handler.postDelayed(updateQueryRunnable, 300)
            }
        }
    }

    private fun isSameLastQuery(query: String) = lastQuery.trim().equals(query.trim(), true).not()

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
        findNavController().navigate(SearchFragmentDirections.actionSearchToDetail(resource))
    }
}