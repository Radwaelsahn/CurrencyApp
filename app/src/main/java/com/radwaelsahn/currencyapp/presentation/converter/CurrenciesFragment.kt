package com.radwaelsahn.currencyapp.presentation.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.radwaelsahn.currencyapp.R
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.databinding.FragmentCurrenciesBinding

import com.radwaelsahn.currencyapp.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.coroutines.flow.collect

import java.io.InputStream

@AndroidEntryPoint
class CurrenciesFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    private val converterViewModel: ConverterViewModel by viewModels()

    private var _binding: FragmentCurrenciesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getData()
//        observeFlowData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initViews() {
        binding.buttonConvert.setOnClickListener {
            convertCurrencies()
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    fun observeFlowData() {
        lifecycleScope.launchWhenStarted {
            converterViewModel.uiFlow.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        showLoading(progress_bar, state.loading)
                    }
                    is Resource.Success -> {
                        showLoading(progress_bar, false)
                        //state.data?.let { showCharacters(it) } ?: showError(state.error)
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


    fun getData() {
//        converterViewModel.getData()
        readCurrencies()
    }

    fun readCurrencies() {
        val input: InputStream = resources.openRawResource(R.raw.currencies)
        val currencyList = converterViewModel.getCurrencyList(input)
        currencyList?.let {
            var names = currencyList.map { it.code }
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner, names
            )

            spinner_from_currency.adapter = adapter
            spinner_from_currency.onItemSelectedListener = this

            spinner_to_currency.adapter = adapter
            spinner_to_currency.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


    private fun convertCurrencies() {
        converterViewModel.convertCurrency(
            spinner_from_currency.selectedItem.toString(),
            spinner_to_currency.selectedItem.toString(),
            et_from.text.toString()
        )

        lifecycleScope.launchWhenStarted {
            converterViewModel.uiFlow.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        showLoading(progress_bar, state.loading)
                    }
                    is Resource.Success -> {
                        showLoading(progress_bar, false)
                        tv_to_currency.text = state.data?.result.toString()
                        //state.data?.let { showCharacters(it) } ?: showError(state.error)
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