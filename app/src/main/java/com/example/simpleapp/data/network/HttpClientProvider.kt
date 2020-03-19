package com.example.simpleapp.data.network

import okhttp3.OkHttpClient

object HttpClientProvider {
    private val instance by lazy {
        OkHttpClient()
    }

    fun get() = instance
}
