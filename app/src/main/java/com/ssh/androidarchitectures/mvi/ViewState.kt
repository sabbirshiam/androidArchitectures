package com.ssh.androidarchitectures.mvi

import com.ssh.androidarchitectures.model.Country

sealed class ViewState {
    object Idle : ViewState()
    object Loading : ViewState()
    data class Countries(val countries: List<Country>) : ViewState()
    data class Error(val error: String?) : ViewState()
}