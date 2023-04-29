package com.hebs.moviedb.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

abstract class BaseFragment : Fragment() {
    protected abstract fun getViewModel(): BaseViewModel
    protected abstract fun getRefreshLayout(): SwipeRefreshLayout?

    private fun showError(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    protected open fun initViewModel() {
        getViewModel().baseFragmentActionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                BaseViewActions.ShowLoading -> showLoading()
                BaseViewActions.HideLoading -> hideLoading()
                is BaseViewActions.Error -> showError(it.message)
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