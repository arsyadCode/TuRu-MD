package com.turu.data.history

import androidx.paging.PagingSource
import androidx.paging.PagingState

//class HistoryPagingSource(private val historyApi: HistoryApi) : PagingSource<Int, GetHistoryUserIdResponse>() {
//
//    override fun getRefreshKey(state: PagingState<Int, GetHistoryUserIdResponse>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetHistoryUserIdResponse> {
//        return try {
//            val position = params.key ?: INITIAL_PAGE_INDEX
//            val responseData = historyApi.getHistories("", "")
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
////                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
//            )
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}