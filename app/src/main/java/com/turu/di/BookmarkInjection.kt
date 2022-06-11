package com.turu.di

import android.content.Context
import com.turu.data.ApiConfig
import com.turu.data.bookmark.BookmarkRepository

object BookmarkInjection {
    fun provideRepository(context: Context): BookmarkRepository {
        val bookmarkApi = ApiConfig().getBookmarkApi()
        return BookmarkRepository(bookmarkApi)
    }
}