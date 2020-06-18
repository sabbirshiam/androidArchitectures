package com.ssh.androidarchitectures.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.model.Country
import io.reactivex.observers.DisposableSingleObserver

class CountriesViewModel: ViewModel(){
    private var countriesService =
        CountriesService()
    private val countries = MutableLiveData<List<String>>()
    private val countriesError = MutableLiveData<Boolean>()
    init {
        fetchCountries()
    }
    private fun fetchCountries() {
        with(countriesService) {
            getCountries()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(values: List<Country>) {
                        android.util.Log.e("SUCCESS::", "returns data $values")
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

    fun getCountries(): LiveData<List<String>> {
        return countries
    }

    fun getCountriesError(): LiveData<Boolean> {
        return countriesError
    }

    fun onRefresh() {
        fetchCountries()
    }
}