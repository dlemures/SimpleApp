package com.example.simpleapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private val instance by lazy {
        Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(HttpClientProvider.get())
            .build()
    }

    fun get(): Retrofit = instance
}
