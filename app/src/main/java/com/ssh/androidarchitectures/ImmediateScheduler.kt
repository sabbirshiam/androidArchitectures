package com.ssh.androidarchitectures

import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulerProvider: BaseSchedulerProvider {
    @NonNull
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    @NonNull
    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    @NonNull
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }
}