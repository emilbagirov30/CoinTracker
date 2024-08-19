package com.emil.mobileuptest


import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    @GET("coins/markets")
    suspend fun getCryptoList(
        @Query("vs_currency") currency: String,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): List<CryptoResponse>
}