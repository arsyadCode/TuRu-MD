package com.turu.data.history

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData

class HistoryRepository(
    private val historyApi: HistoryApi
    ) {
    fun getHistories(token: String, id: String): LiveData<PagingData<GetHistoryUserIdResponseItem>> {
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