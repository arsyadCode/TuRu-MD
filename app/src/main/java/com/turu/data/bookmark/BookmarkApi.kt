package com.turu.data.bookmark

import retrofit2.Call
import retrofit2.http.*

interface BookmarkApi {

    @GET("bookmarks/users")
    fun getBookmark(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ) : Call<BookmarkResponse>

    @POST("bookmarks/users")
    fun createBookmark(
        @Header("Authorization") token: String,
        @Field("text") text: String
    ) : Call<CreateBookmarkResponse>
}