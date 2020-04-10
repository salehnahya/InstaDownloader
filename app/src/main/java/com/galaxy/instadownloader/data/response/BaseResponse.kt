package com.majidalfuttaim.terminal.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("responseMessage")
    open var responseMessage: String? = ""

    @SerializedName("responseCode")
    open var responseCode: String? = ""

}