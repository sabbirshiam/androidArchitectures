package com.ssh.androidarchitectures

import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull

interface BaseSchedulerProvider {
    @NonNull
    fun computation(): Scheduler

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler
}