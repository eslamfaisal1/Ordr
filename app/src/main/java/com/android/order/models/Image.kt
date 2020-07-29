package com.android.order.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Image(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null
)