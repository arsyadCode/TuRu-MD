package com.turu.data.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(

    @field:SerializedName("BookmarkResponse")
    val bookmarkResponse: List<BookmarkResponseItem>
)