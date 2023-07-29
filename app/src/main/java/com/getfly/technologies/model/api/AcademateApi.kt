package com.getfly.technologies.model.api

import android.util.Log
import com.getfly.technologies.model.response.LoginInput
import com.getfly.technologies.model.response.LoginResponse
import com.getfly.technologies.model.response.PersonalDetailsResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class AcademateWebService {
    var api: AcademateApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://vppcoe-va.getflytechnologies.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(AcademateWebService.AcademateApi::class.java)
    }

    interface AcademateApi {

        @FormUrlEncoded
        @POST("api/login")
        suspend fun postLogin(
            @Field("email") email: String,
            @Field("password") password: String
        ): Response<LoginResponse>

//        @POST("api/login")
//        suspend fun postLogin2(
//            @Body post: LoginInput
//        ): Response<LoginResponse>

//        @FormUrlEncoded
//        @POST("/post")
//        suspend fun postLogin3(@FieldMap params: HashMap<String?, String?>): Response<LoginInput>


        @GET("api/admission/personalDetails")
        suspend fun getPersonalDetails(
            @Query("uid")
            uid : String?
        ) : Response<PersonalDetailsResponse>

    }

}