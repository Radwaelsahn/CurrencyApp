package com.radwaelsahn.currencyapp.presentation.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.radwaelsahn.currencyapp.R
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.databinding.FragmentHistoryBinding
import com.radwaelsahn.currencyapp.presentation.BaseFragment
import com.radwaelsahn.currencyapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.coroutines.flow.collect
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.radwaelsahn.currencyapp.data.models.HistoryItem

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class HistoryFragment : BaseFragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        _binding?.viewModel = historyViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.base = arguments?.getString(Constants.KEY_BASE_CURRENCY)!!
        historyViewModel.to = arguments?.getString(Constants.KEY_TO_CURRENCY)!!
        historyViewModel.amount = arguments?.getString(Constants.KEY_AMOUNT_FROM)!!

        historyViewModel.getHistory(
            historyViewModel.base, historyViewModel.to
        )
        observeGetData()
    }


    private fun observeGetData() {

        with(historyViewModel) {
            lifecycleScope.launchWhenStarted {
                uiFlowGet.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            showLoading(progress_bar, state.loading)
                        }
                        is Resource.Success -> {
                            showLoading(progress_bar, false)
                            state.data?.let {
                                showHistoryList(it)
                            } ?: showError(state.error)
                        }
                        is Resource.Error -> {
                            showLoading(progress_bar, false)
                            state.error?.let {
                                showError(state.error)
                            }
                        }

                    }
                }
            }
        }

    }

    private fun showHistoryList(list: MutableList<HistoryItem>) {
        binding.rvCurrunciesFrom.setHasFixedSize(true)
        var adapter = HistoryAdapter()
        binding.rvCurrunciesFrom.adapter = adapter

        adapter.setHistoryList(
            list.toList()
//            arrayListOf(
//                HistoryItem("2022/4/22", "ASD", "12.232"),
//                HistoryItem("2022/4/23", "qwe", "12.232"),
//                HistoryItem("2022/4/24", "ERE", "12.232")
//            )
        )

        val dividerItemDecoration =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.rvCurrunciesFrom.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}