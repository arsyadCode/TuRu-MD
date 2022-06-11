package com.turu.data.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.turu.data.history.response.GetHistoryUserIdResponseItem

class HistoryRepository(
    private val historyApi: HistoryApi
    ) {
    fun getHistories(token: String, id: String): LiveData<PagingData<GetHistoryUserIdResponseItem>> {
        Log.d("HistoryFLow", "getHistories()")
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                HistoryPagingSource(token, id, historyApi)
            }
        ).liveData
    }
}