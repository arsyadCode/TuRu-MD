package com.turu.ui.register

import androidx.lifecycle.ViewModel
import com.turu.model.UserPreference

class RegisterViewModel(private val perf: UserPreference): ViewModel() {

    companion object {
        const val TAG = "RegisterViewModel"
    }
}