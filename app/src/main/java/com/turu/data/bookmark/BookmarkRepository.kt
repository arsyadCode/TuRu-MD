package com.turu.data.bookmark

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.turu.data.bookmark.response.BookmarkResponseItem

class BookmarkRepository ( private val bookmarkApi: BookmarkApi) {

    fun getBookmarks(token: String, id: String): LiveData<PagingData<BookmarkResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                BookmarkPagingSource(token,id, bookmarkApi)
            }
        ).liveData
    }
}