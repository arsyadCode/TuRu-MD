package com.turu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turu.di.HistoryInjection
import com.turu.model.UserPreference
import com.turu.ui.history.HistoryViewModel
import com.turu.ui.login.LoginViewModel
import com.turu.ui.register.RegisterViewModel
import com.turu.ui.texttoimage.TextToImageViewModel

class ViewModelFactory (private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(TextToImageViewModel::class.java) -> {
                TextToImageViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}