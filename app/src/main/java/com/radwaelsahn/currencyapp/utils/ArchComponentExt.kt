package com.radwaelsahn.currencyapp.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(
    owner: LifecycleOwner,
    liveData: LiveData<T>,
    action: (t: T) -> Unit
) {
    liveData.observe(owner, Observer { it?.let { t -> action(t) } })
}


fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, action: (t: Event<T>) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
    ViewModelProvider(this, viewModelFactory)[T::class.java]