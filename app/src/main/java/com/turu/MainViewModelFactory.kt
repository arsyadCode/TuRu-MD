package com.turu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turu.model.UserPreference
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val pref: UserPreference): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}