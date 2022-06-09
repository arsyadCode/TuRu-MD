package com.turu.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turu.di.HistoryInjection
import com.turu.model.UserPreference

class HistoryViewModelFactory (private val context: Context, private val pref: UserPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(HistoryInjection.provideRepository(context), pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}