package com.ssh.androidarchitectures.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

class CountriesViewModel(
    private val repository: CountryRepository,
    private val scheduler: BaseSchedulerProvider
) : ViewModel() {

    private val countries = MutableLiveData<List<String>>()
    private val countriesError = MutableLiveData<Boolean>()

    fun start() {
        fetchCountries()
    }

    private fun fetchCountries() {
        with(repository) {
            getCountries()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(values: List<Country>) {
                        Timber.i("Success returns Data $values")
                        val countriesName = values.map { it.name }
                        countries.value = countriesName
                        countriesError.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        countriesError.value = true
                    }
                })
        }
    }

    fun getCountries(): LiveData<List<String>> = countries

    fun getCountriesError(): LiveData<Boolean> = countriesError

    fun onRefresh() {
        fetchCountries()
    }
}