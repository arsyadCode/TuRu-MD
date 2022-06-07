package com.turu.data.history

import com.turu.model.history.CreateHistoryRequest
import retrofit2.Call
import retrofit2.http.*

interface HistoryApi {

    @POST("histories")
    fun createHistory(
        @Header("Authorization") token: String,
        @Body req: CreateHistoryRequest
    ): Call<CreateHistoryResponse>

    @GET("histories/{id}")
    fun getHistories(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<GetHistoryUserIdResponse>
}