package com.getfly.technologies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.response.LoginInput
import com.getfly.technologies.model.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainScreenViewModel(private val repository: AcademateRepository): ViewModel() {
    var LoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.postLogin(email,password)
            LoginResponse.value = response
        }
    }

//    fun postLogin2(post : LoginInput) {
//        viewModelScope.launch {
//            val response = repository.postLogin2(post)
//            LoginResponse.value = response
//        }
//    }
}