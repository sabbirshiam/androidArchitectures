package com.ssh.androidarchitectures.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssh.androidarchitectures.mvi.MviCountriesViewModel
import com.ssh.androidarchitectures.mvvm.CountriesViewModel
import com.ssh.androidarchitectures.repositories.CountryRepository

class ViewModelsFactory(private val repository: CountryRepository, private val scheduler: ScheduleProvider): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MviCountriesViewModel::class.java)) {
            return MviCountriesViewModel(repository, scheduler) as T
        }
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            return CountriesViewModel(repository, scheduler) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}