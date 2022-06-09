package com.turu.di

import android.content.Context
import com.turu.data.ApiConfig
import com.turu.data.history.HistoryApi
import com.turu.data.history.HistoryRepository

object HistoryInjection {
    fun provideRepository(context: Context): HistoryRepository {
        val historyApi = ApiConfig().getHistoryApi()
        return HistoryRepository(historyApi)
    }
}