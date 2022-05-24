package com.turu.data.bookmark

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BookmarkPagingSource (token: String, private val bookmarkApi: BookmarkApi) : PagingSource<Int, BookmarkResponseItem>() {
    private val token : String = token

    override fun getRefreshKey(state: PagingState<Int, BookmarkResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookmarkResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = bookmarkApi.getBookmark( token ,params.loadSize)
            LoadResult.Page(
                data = responseData.bookmarkResponse,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.bookmarkResponse.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}