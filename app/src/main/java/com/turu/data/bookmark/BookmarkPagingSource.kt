package com.turu.data.bookmark

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.turu.data.bookmark.response.BookmarkResponseItem

class BookmarkPagingSource (
    private val token: String,
    private val id: String,
    private val bookmarkApi: BookmarkApi
    ) : PagingSource<Int, BookmarkResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, BookmarkResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookmarkResponseItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = bookmarkApi.getBookmarks( token ,id, page,params.loadSize)
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}