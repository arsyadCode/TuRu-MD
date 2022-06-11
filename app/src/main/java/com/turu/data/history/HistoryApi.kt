package com.turu.data.history

import com.turu.data.history.response.CreateHistoryResponse
import com.turu.data.history.response.GetHistoryUserIdResponseItem
import com.turu.model.history.CreateHistoryRequest
import retrofit2.Call
import retrofit2.http.*

interface HistoryApi {

    @POST("histories")
    fun createHistory(
        @Header("Authorization") token: String,
        @Body req: CreateHistoryRequest
    ): Call<CreateHistoryResponse>

    @GET("histories/users/{id}")
    suspend fun getHistories(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<GetHistoryUserIdResponseItem>
}