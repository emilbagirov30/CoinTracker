package com.emil.mobileuptest

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class CryptoListFragment(private var currentCurrency:String) : Fragment() {
    private lateinit var viewModel: CryptoViewModel
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var rvCryptoList: RecyclerView
    private lateinit var chipGroup: ChipGroup
    private lateinit var chipUsd: Chip
    private lateinit var chipRub: Chip
    private lateinit var fragmentContainer: FrameLayout
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
        fragmentContainer =view.findViewById(R.id.fragment_inner_container)
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
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val errorFragment = ErrorFragment (currentCurrency)
            fragmentTransaction.replace(R.id.fragment_inner_container, errorFragment)
            fragmentTransaction.commit()
            chipUsd.isEnabled = false
            chipRub.isEnabled = false
            MainActivity.loading.visibility = View.GONE
            rvCryptoList.visibility = View.GONE
        }
        initialRequest ()
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

private fun initialRequest (){
    fetchCryptos(currentCurrency)
    if (currentCurrency=="usd")
        chipUsd.isChecked = true
    else
        chipRub.isChecked = true
}
    }


