package com.ssh.androidarchitectures.mvi

sealed class MviIntent {
    object FetchCountries: MviIntent()
}