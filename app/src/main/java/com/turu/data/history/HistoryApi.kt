package com.turu.data.history

import retrofit2.http.POST

interface HistoryApi {

    @POST
    fun createHistory()
}