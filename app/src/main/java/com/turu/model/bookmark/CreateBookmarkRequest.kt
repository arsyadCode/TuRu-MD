package com.turu.model.bookmark

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateBookmarkRequest {

    @field: SerializedName("text")
    @Expose
    var text: String? = null

}