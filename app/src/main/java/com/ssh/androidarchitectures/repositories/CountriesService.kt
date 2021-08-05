package com.ssh.androidarchitectures.repositories

import com.ssh.androidarchitectures.BuildConfig
import com.ssh.androidarchitectures.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface CountryRepository{
    fun getCountries(): Single<List<Country>>
    suspend fun getCountriess(): List<Country>
}

class CountriesService: CountryRepository {

    var countriesApi: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        countriesApi = retrofit.create(CountriesApi::class.java)
    }

    override fun getCountries(): Single<List<Country>> {
        return countriesApi.getCountries()
    }

    override suspend fun getCountriess(): List<Country> {
        return countriesApi.getCountriess()
    }
}