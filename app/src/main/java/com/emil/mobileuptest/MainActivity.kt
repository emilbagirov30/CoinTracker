package com.emil.mobileuptest

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

companion object {
  lateinit var loading: ProgressBar
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       loading = findViewById(R.id.pb_loading)
        val cryptoListFragment = CryptoListFragment("usd")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, cryptoListFragment)
            commit()
        }
    }
}
