package com.hebs.moviedb.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hebs.moviedb.R

abstract class BaseFragment : Fragment() {
    protected abstract fun getViewModel(): BaseViewModel
    protected abstract fun getRefreshLayout(): SwipeRefreshLayout?

    private fun showError() {
        Toast.makeText(requireContext(), requireContext().getString(R.string.section_unavailable), Toast.LENGTH_LONG).show()
    }

    protected open fun initViewModel() {
        getViewModel().baseFragmentActionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                BaseViewActions.ShowLoading -> showLoading()
                BaseViewActions.HideLoading -> hideLoading()
                BaseViewActions.Error -> showError()
            }
        }
    }

    private fun showLoading() {
        getRefreshLayout()?.isRefreshing = true
    }

    private fun hideLoading() {
        getRefreshLayout()?.isRefreshing = false
    }

}