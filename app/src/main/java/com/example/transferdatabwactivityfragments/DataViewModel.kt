package com.example.demofragmentapp.passDataFragmentToActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {

    var fragmen1Data: MutableLiveData<String> ?= null
    var activityData: MutableLiveData<String> ?= null

    init {
        fragmen1Data = MutableLiveData()
        activityData = MutableLiveData()
    }

    fun setFragmentData(data : String){
        fragmen1Data?.value = data
    }

    fun getFragmentData(): LiveData<String>{
        return fragmen1Data!!
    }

    fun setActivityData(data : String){
        activityData?.value = data
    }

    fun getActivityData(): LiveData<String>{
        return activityData!!
    }
}