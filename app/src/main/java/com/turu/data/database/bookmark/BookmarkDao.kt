package com.turu.data.database.bookmark

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.turu.data.bookmark.BookmarkResponseItem

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(quote: List<BookmarkResponseItem>)

    @Query("SELECT * FROM bookmark")
    fun getAllBookmark(): PagingSource<Int, BookmarkResponseItem>

    @Query("DELETE FROM bookmark")
    suspend fun deleteAll()
}