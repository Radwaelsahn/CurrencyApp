package com.radwaelsahn.currencyapp.presentation.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.radwaelsahn.currencyapp.R
import com.radwaelsahn.currencyapp.data.Resource
import com.radwaelsahn.currencyapp.databinding.FragmentCurrenciesBinding

import com.radwaelsahn.currencyapp.presentation.BaseFragment
import com.radwaelsahn.currencyapp.utils.Constants
import com.radwaelsahn.currencyapp.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currencies.*
import kotlinx.coroutines.flow.collect

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
    ): View {

        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        _binding?.currenciesViewModel = converterViewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getData()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(converterViewModel) {
            observe(viewLifecycleOwner, convertedValue, ::showResult)
            observe(
                viewLifecycleOwner,
                currenciesList,
                ::showCurrenciesFromList
            )
            observe(viewLifecycleOwner, selectFrom, ::selectSpinnerFrom)
            observe(viewLifecycleOwner, selectTo, ::selectSpinnerTo)
            observe(viewLifecycleOwner, fromValue, ::showFrom)
        }
    }

    private fun showFrom(value: String) {
        binding.etFrom.setText(value)
    }

    private fun selectSpinnerFrom(position: Int) {
        binding.autoCompleteFrom.setSelection(position)
    }

    private fun selectSpinnerTo(position: Int) {
        binding.autoCompleteTo.setSelection(position)
    }

    private fun goToHistory() {
        val base = binding.autoCompleteFrom.text.toString()//?.let { it } ?: ""
        val to = binding.autoCompleteTo.text.toString()//?.let { it } ?: ""


        val bundle =
            bundleOf(
                "${Constants.KEY_BASE_CURRENCY}" to base,
                "${Constants.KEY_TO_CURRENCY}" to to
            )
        findNavController().navigate(R.id.action_FirstFragment_to_HistoryFragment, bundle)
    }

    private fun showResult(result: String) {
        binding.etTo.setText(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initViews() {
        binding.buttonConvert.setOnClickListener {
            convertCurrencies()
        }

        binding.buttonDetails.setOnClickListener {
            goToHistory()
        }

        binding.buttonSwap.setOnClickListener {

            val currencyFrom = binding.autoCompleteFrom.text.toString()
            val currencyTo = binding.autoCompleteTo.text.toString()
            binding.autoCompleteFrom.setText(currencyTo)
            binding.autoCompleteTo.setText(currencyFrom)

            val from = binding.etFrom.text.toString()
            val to = binding.etTo.text.toString()
            binding.etFrom.setText(to)
            binding.etTo.setText(from)

//            converterViewModel.swap(
//                binding.spinnerFromCurrency.selectedItem.toString(),
//                binding.spinnerToCurrency.selectedItem.toString(), et_from.text.toString()
//            )
        }

        binding.etFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.autoCompleteFrom.text?.let {
                    converterViewModel.onValueChanged(
                        binding.autoCompleteFrom.text.toString(),
                        binding.autoCompleteTo.text.toString(), s.toString()
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun observeGetData() {
        val base = binding.autoCompleteFrom.text?.let { it } ?: ""
        with(converterViewModel) {
            callGetCurrenciesAPI(base.toString())
            lifecycleScope.launchWhenStarted {
                uiFlowGet.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            showLoading(progress_bar, state.loading)
                        }
                        is Resource.Success -> {
                            showLoading(progress_bar, false)
                            state.data?.let {
                                showCurrenciesFromList(it.keys.toList())
                                showCurrenciesToList(it.keys.toList())
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


    private fun getData() {
        observeGetData()
    }

//    private fun readCurrenciesFromJson() {
//        val input: InputStream = resources.openRawResource(R.raw.currencies)
//        converterViewModel.getStaticCurrencyList(input)
//    }

    private fun showCurrenciesFromList(names: List<String>) {

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner, listOf("EUR")
        )

        binding.autoCompleteFrom.setAdapter(adapter)
//        binding.autoCompleteFrom.onItemSelectedListener = this
        binding.autoCompleteFrom.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                convertCurrencies()
            }
        binding.autoCompleteFrom.setAdapter(adapter)

        binding.autoCompleteFrom.setText("EUR")
    }

    private fun showCurrenciesToList(names: List<String>) {
        if (names.isNotEmpty()) {
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner, names
            )
            adapter.filter.filter(null)
            binding.autoCompleteTo.setAdapter(adapter)
//            binding.autoCompleteTo.onItemSelectedListener = this

            binding.autoCompleteTo.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    convertCurrencies()
                }
            binding.autoCompleteTo.setText(names[0])
            convertCurrencies()
//            binding.autoCompleteTo.setse( 0)

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.autoCompleteFrom -> {
                convertCurrencies()
            }

            R.id.autoCompleteTo -> {
                convertCurrencies()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun convertCurrencies() {
        converterViewModel.convertCurrency(
            autoCompleteFrom.text.toString(),
            autoCompleteTo.text.toString(),
            et_from.text.toString()
        )

        lifecycleScope.launchWhenStarted {
            converterViewModel.uiFlowConvert.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        showLoading(progress_bar, state.loading)
                    }
                    is Resource.Success -> {
                        showLoading(progress_bar, false)
                        binding.etTo.setText(state.data)
                    }
                    is Resource.Error -> {
                        showLoading(progress_bar, false)
                        state.error?.let {
                            showError(state.error)
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

}