package com.turu.data.bookmark

import com.turu.data.bookmark.response.BookmarkResponseItem
import com.turu.data.bookmark.response.CreateBookmarkResponse
import com.turu.data.bookmark.response.DeleteBookmarkResponse
import com.turu.model.bookmark.CreateBookmarkRequest
import retrofit2.Call
import retrofit2.http.*

interface BookmarkApi {

    @POST("bookmarks")
    fun createBookmark(
        @Header("Authorization") token: String,
        @Body req: CreateBookmarkRequest
    ) : Call<CreateBookmarkResponse>

    @GET("bookmarks/users/{id}")
    suspend fun getBookmarks(
        @Header("Authorization") token: String,
        @Path("ID") id: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<BookmarkResponseItem>

    @DELETE("bookmarks/{id}")
    fun deleteBookmark(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<DeleteBookmarkResponse>
}