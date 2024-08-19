package com.emil.mobileuptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val _cryptoList = MutableLiveData<List<CryptoResponse>>()
    val cryptoList: LiveData<List<CryptoResponse>> get() = _cryptoList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCryptos(currency: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCryptoList(currency)
                _cryptoList.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
