package com.getfly.technologies.model.response


import com.google.gson.annotations.SerializedName

data class InitiatePaymentResponse(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("status")
    val status: Int
)