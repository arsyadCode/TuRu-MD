package com.turu.data.history

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.turu.data.history.response.GetHistoryUserIdResponseItem

class HistoryPagingSource(
    private val token: String,
    private val id: String,
    private val historyApi: HistoryApi
    ) : PagingSource<Int, GetHistoryUserIdResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, GetHistoryUserIdResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetHistoryUserIdResponseItem> {
        return try {
            Log.d("HistoryFLow", "History Paging Source suspend load")
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = historyApi.getHistories(token, id, page, params.loadSize)
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}