package com.emil.mobileuptest

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CryptoAdapter(
    private var cryptos: List<CryptoResponse>,
    private var currency: String
) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptos[position]
        holder.bind(crypto, currency)
    }

    override fun getItemCount(): Int = cryptos.size


    fun updateData(newCryptos: List<CryptoResponse>, newCurrency: String) {
        cryptos = newCryptos
        currency = newCurrency
        notifyDataSetChanged()
    }

    class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivLogo: ImageView = itemView.findViewById(R.id.iv_logo)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvSymbol: TextView = itemView.findViewById(R.id.tv_symbol)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val tvDiffPercent: TextView = itemView.findViewById(R.id.tv_diff_percent)

        fun bind(crypto: CryptoResponse, currency: String) {
            tvName.text = crypto.name
            tvSymbol.text = crypto.symbol.uppercase()


            val currencySymbol = if (currency == "rub") "₽" else "$"
            tvPrice.text = "$currencySymbol${crypto.currentPrice}"


            val diffPercentage = crypto.priceChangePercentage24h
            val formattedDiff = if (diffPercentage >= 0) "+${diffPercentage}%" else "${diffPercentage}%"
            tvDiffPercent.text = formattedDiff


            val colorRes = if (diffPercentage >= 0) R.color.item_difference_plus else R.color.item_difference_minus
            tvDiffPercent.setTextColor(itemView.context.getColor(colorRes))

            Glide.with(itemView.context)
                .load(crypto.image)
                .into(ivLogo)
        }
    }
}
