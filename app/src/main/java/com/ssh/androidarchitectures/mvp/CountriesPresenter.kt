package com.ssh.androidarchitectures.mvp

import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.repositories.CountriesService
import com.ssh.androidarchitectures.model.Country
import com.ssh.androidarchitectures.repositories.CountryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountriesPresenter(
    private val view: MvpContractor.View,
    private val repository: CountryRepository,
    private val scheduler: BaseSchedulerProvider
) : MvpContractor.Presenter {


    init {
        view.presenter = this
    }

    override fun start() {
        fetchCountries()
    }

    private fun fetchCountries() {
        with(repository) {
            getCountries()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(values: List<Country>) {
                        val countriesName = values.map { it.name }
                        view.showCountriesName(countriesName)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.showError()
                    }
                })
        }
    }

    override fun onRetry() {
        fetchCountries()
    }
}