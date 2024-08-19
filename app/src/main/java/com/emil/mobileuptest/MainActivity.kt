package com.emil.mobileuptest

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CryptoViewModel
    private lateinit var cryptoAdapter: CryptoAdapter

    private lateinit var rvCryptoList: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var chipGroup: ChipGroup
    private lateinit var chipUsd: Chip
    private lateinit var chipRub: Chip

    private var currentCurrency = "usd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvCryptoList = findViewById(R.id.rv_crypto_list)
        pbLoading = findViewById(R.id.pb_loading)
        chipGroup = findViewById(R.id.chip_group)
        chipUsd = findViewById(R.id.chip_usd)
        chipRub = findViewById(R.id.chip_rub)


        viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        cryptoAdapter = CryptoAdapter(emptyList(), currentCurrency)
        rvCryptoList.layoutManager = LinearLayoutManager(this)
        rvCryptoList.adapter = cryptoAdapter


        viewModel.cryptoList.observe(this) { cryptos ->
            cryptoAdapter.updateData(cryptos, currentCurrency)
            pbLoading.visibility = View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            pbLoading.visibility = View.GONE
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
    }

    private fun fetchCryptos(currency: String) {
        pbLoading.visibility = View.VISIBLE
        viewModel.fetchCryptos(currency)
    }
}
