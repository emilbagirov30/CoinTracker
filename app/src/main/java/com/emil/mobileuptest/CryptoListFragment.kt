package com.emil.mobileuptest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class CryptoListFragment : Fragment() {
    private lateinit var viewModel: CryptoViewModel
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var rvCryptoList: RecyclerView
    private lateinit var chipGroup: ChipGroup
    private lateinit var chipUsd: Chip
    private lateinit var chipRub: Chip
    private var currentCurrency = "usd"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_crypto_list, container, false)
        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        rvCryptoList = view.findViewById(R.id.rv_crypto_list)
        chipGroup = view.findViewById(R.id.chip_group)
        chipUsd =view.findViewById(R.id.chip_usd)
        chipRub =view.findViewById(R.id.chip_rub)
        cryptoAdapter = CryptoAdapter(emptyList(), currentCurrency)
        rvCryptoList.layoutManager = LinearLayoutManager(requireContext())
        rvCryptoList.adapter = cryptoAdapter
        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        cryptoAdapter = CryptoAdapter(emptyList(), currentCurrency)
        rvCryptoList.layoutManager = LinearLayoutManager(requireActivity())
        rvCryptoList.adapter = cryptoAdapter


        viewModel.cryptoList.observe(requireActivity()) { cryptos ->
            cryptoAdapter.updateData(cryptos, currentCurrency)
            MainActivity.loading.visibility = View.GONE
            rvCryptoList.visibility = View.VISIBLE
        }

        viewModel.error.observe(requireActivity()) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            MainActivity.loading.visibility = View.GONE
        }


        fetchCryptos(currentCurrency)
        chipUsd.isChecked = true
        chipUsd.setOnClickListener {
            currentCurrency = "usd"
            fetchCryptos(currentCurrency)
        }

        chipRub.setOnClickListener {
            currentCurrency = "rub"
            fetchCryptos(currentCurrency)
        }
        return  view
    }

    private fun fetchCryptos(currency: String) {
        MainActivity.loading.visibility = View.VISIBLE
        rvCryptoList.visibility = View.GONE
        viewModel.fetchCryptos(currency)
    }

    }


