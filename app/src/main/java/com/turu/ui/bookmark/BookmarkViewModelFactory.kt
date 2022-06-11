package com.turu.ui.bookmark

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turu.di.BookmarkInjection
import com.turu.model.UserPreference

class BookmarkViewModelFactory(private val context: Context, private val pref: UserPreference) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(BookmarkInjection.provideRepository(context), pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}