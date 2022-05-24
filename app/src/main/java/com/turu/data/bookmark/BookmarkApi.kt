package com.turu.data.bookmark

import retrofit2.Call
import retrofit2.http.*

interface BookmarkApi {

    @GET("bookmarks/users")
    suspend fun getBookmark(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ) : BookmarkResponse

    @POST("bookmarks/users")
    fun createBookmark(
        @Header("Authorization") token: String,
        @Field("text") text: String
    ) : Call<CreateBookmarkResponse>
}