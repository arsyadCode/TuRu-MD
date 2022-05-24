package com.turu.data.bookmark

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.turu.data.database.BookmarkDatabase

class BookmarkRepository (private val bookmarkDatabase: BookmarkDatabase, private val bookmarkApi: BookmarkApi) {

    fun getAllBookmark(token: String): LiveData<PagingData<BookmarkResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = BookmarkRemoteMediator(token, bookmarkDatabase, bookmarkApi),
            pagingSourceFactory = {
                bookmarkDatabase.bookmarkDao().getAllBookmark()
            }
        ).liveData
    }
}