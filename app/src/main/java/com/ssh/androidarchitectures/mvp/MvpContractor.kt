package com.ssh.androidarchitectures.mvp

import androidx.annotation.NonNull

interface MvpContractor {
    interface Presenter {
        fun start()
        fun onRetry()
    }

    interface View {
        var presenter: Presenter
        //fun setPresenter(@NonNull presenter: Presenter)
        fun showCountriesName(countries: List<String>)
        fun showError()
    }
}