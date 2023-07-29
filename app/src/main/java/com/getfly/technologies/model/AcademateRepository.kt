package com.getfly.technologies.model

import com.getfly.technologies.model.api.AcademateWebService
import com.getfly.technologies.model.response.LoginInput
import com.getfly.technologies.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.POST

class AcademateRepository(private val webService : AcademateWebService = AcademateWebService()) {
    suspend fun postLogin( email: String, password: String): Response<LoginResponse> {
        return webService.api.postLogin(email,password)
    }
//    suspend fun postLogin2(post : LoginInput): Response<LoginInput> {
//        return webService.api.postLogin2(post)
//    }
}