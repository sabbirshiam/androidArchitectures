package com.ssh.androidarchitectures.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssh.androidarchitectures.BaseSchedulerProvider
import com.ssh.androidarchitectures.repositories.CountryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MviCountriesViewModel(
    private val repository: CountryRepository,
    private val scheduler: BaseSchedulerProvider
) : ViewModel() {

    val countryIntent = Channel<MviIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ViewState>(ViewState.Idle)
    val state: StateFlow<ViewState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            countryIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is MviIntent.FetchCountries -> fetchCountries()
                }
            }
        }
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            _state.value = try {
                ViewState.Countries(repository.getCountriess())
            } catch (e: Exception) {
                ViewState.Error(e.localizedMessage)
            }
        }
    }
}