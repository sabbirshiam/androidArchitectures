package com.ssh.androidarchitectures.repositories

import com.ssh.androidarchitectures.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    fun getCountries(): Single<List<Country>>
}