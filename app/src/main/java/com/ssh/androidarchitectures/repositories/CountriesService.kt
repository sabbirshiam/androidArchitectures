package com.ssh.androidarchitectures.repositories

import com.ssh.androidarchitectures.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
interface CountryRepository{
    fun getCountries(): Single<List<Country>>
}
class CountriesService: CountryRepository {
    companion object {
        val BASE_URL = "https://restcountries.eu/rest/v2/"
    }

    var countriesApi: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        countriesApi = retrofit.create(CountriesApi::class.java)
    }

    override fun getCountries(): Single<List<Country>> {
        return countriesApi.getCountries()
    }
}