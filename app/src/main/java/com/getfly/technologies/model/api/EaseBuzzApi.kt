package com.getfly.technologies.model.api

import com.getfly.technologies.model.response.InitiatePaymentResponse
import com.getfly.technologies.model.response.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class EaseBuzzWebService {
    var easeBuzzApi: EaseBuzzApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://testpay.easebuzz.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        easeBuzzApi = retrofit.create(EaseBuzzApi::class.java)
    }

    interface EaseBuzzApi {

        @FormUrlEncoded
        @POST("payment/initiateLink")
        suspend fun postInitiatePayment(
            @Field("status") status: Int,
            @Field("data") data: String
        ): Response<InitiatePaymentResponse>
    }
}