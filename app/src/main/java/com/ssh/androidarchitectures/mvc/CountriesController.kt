package com.ssh.androidarchitectures.mvc

import android.util.Log
import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.observers.DisposableSingleObserver

class CountriesController(
    private var view: MVCActivity,
    private val repository: CountryRepository,
    private val scheduleProvider: BaseSchedulerProvider
) {

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        with(repository) {
            getCountries()
                .subscribeOn(scheduleProvider.io())
                .observeOn(scheduleProvider.ui())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(values: List<Country>) {
                        Log.e("SUCCESS::", "returns data $values")
                        val countriesName = values.map { it.name }
                        view.showCountriesName(countriesName)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.onError()
                    }
                })
        }
    }

    fun onRefresh() {
        fetchCountries()
    }
}