package com.turu.data.bookmark

import androidx.lifecycle.LiveData
import androidx.paging.*

class BookmarkRepository ( private val bookmarkApi: BookmarkApi) {

    fun getAllBookmark(token: String): LiveData<PagingData<BookmarkResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                BookmarkPagingSource("", bookmarkApi)
            }
        ).liveData
    }
}