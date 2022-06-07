package com.turu.model.history

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateHistoryRequest {

    @field: SerializedName("text")
    @Expose
    var text: String? = null

}