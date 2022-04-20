package com.anushka.viewmodeldemo2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(startingCount: Int): ViewModel() {
    private var count = MutableLiveData<Int>()
    val countData: LiveData<Int>
    get() = count

    var sum = MutableLiveData<String>()

    init {
        count.postValue(startingCount)
    }

    fun sumCount() {
        count.postValue(sum.value?.let { count.value?.plus(it.toInt()) })
    }
}