package com.radwaelsahn.currencyapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment: Fragment()
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeFlowData()
        getData()
    }
    abstract fun initViews()
    abstract fun observeFlowData()
    abstract fun getData()

    fun showLoading(progressBar: ProgressBar?, show: Boolean) {
        progressBar?.let {
            if (show) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    fun showError(message: String?) {
        message?.let {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }
}