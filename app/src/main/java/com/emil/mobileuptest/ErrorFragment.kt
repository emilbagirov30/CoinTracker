package com.emil.mobileuptest

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class ErrorFragment (var currentCurrency:String) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view = inflater.inflate(R.layout.fragment_error, container, false)
        val update = view.findViewById<Button>(R.id.bt_update)
        update.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val cryptoListFragment = CryptoListFragment(currentCurrency)
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, cryptoListFragment)
            transaction.commit()
        }
        return view
    }


}