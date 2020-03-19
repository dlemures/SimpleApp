package com.example.simpleapp.data.network

import com.example.simpleapp.model.CatFact
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactApiClient {
    @GET("facts/random?animal_type=cat")
    fun getCatFacts(@Query("amount") amount: Int): Single<List<CatFact>>
}
