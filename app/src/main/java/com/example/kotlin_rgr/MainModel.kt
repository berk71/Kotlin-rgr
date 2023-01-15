package com.example.kotlin_rgr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainModel: ViewModel() {
    val currentData= MutableLiveData<Day>()
    val liveDataList= MutableLiveData< List<Day>>()
}