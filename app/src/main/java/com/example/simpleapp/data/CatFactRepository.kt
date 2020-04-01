package com.example.simpleapp.data

import com.example.simpleapp.data.network.CatFactApiClient
import com.example.simpleapp.data.network.RetrofitProvider
import com.example.simpleapp.model.CatFact
import io.reactivex.Single

// It would be better to wrap the return value as a Data class,
// that way we don't expose the network errors to the outside,
// as now the VM is dealing with the network errors.
// ENCAPSULATION AND SEPARATION OF CONCERNS!
class CatFactRepository {
    fun getCatFacts(from: Int, length: Int): Single<List<CatFact>> {
        val apiClient = RetrofitProvider.get().create(CatFactApiClient::class.java)
        return apiClient.getCatFacts(from + length)
            .map { list -> list.subList(from, from + length) }
    }
}
