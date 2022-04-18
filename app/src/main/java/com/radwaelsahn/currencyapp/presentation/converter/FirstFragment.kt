package com.radwaelsahn.currencyapp.presentation.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radwaelsahn.currencyapp.R
import com.radwaelsahn.currencyapp.data.models.Currency
import com.radwaelsahn.currencyapp.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.BufferedReader
import java.io.InputStream

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val converterViewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        readCurrencies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readCurrencies() {
//        val input: InputStream = resources.openRawResource(R.raw.currencies)
//        val currencyList = converterViewModel.getCurrencyList(input)
//
//        var names = currencyList.map { it.name }
//        val adapter = ArrayAdapter(
//            requireContext(),
//            R.layout.item_spinner, names
//        )
//
//        spinner_from_currency.adapter = adapter
//        spinner_from_currency.onItemSelectedListener = this
//
//        spinner_to_currency.adapter = adapter
//        spinner_to_currency.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}