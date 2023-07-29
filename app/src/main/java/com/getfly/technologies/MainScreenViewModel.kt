package com.getfly.technologies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getfly.technologies.model.AcademateRepository
import com.getfly.technologies.model.response.DocResponse
import com.getfly.technologies.model.response.LoginInput
import com.getfly.technologies.model.response.LoginResponse
import com.getfly.technologies.model.response.PersonalDetailsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainScreenViewModel(private val repository: AcademateRepository): ViewModel() {
    var LoginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    var PersonalDeatailResponse: MutableLiveData<Response<PersonalDetailsResponse>> = MutableLiveData()
    var DocDeatailResponse: MutableLiveData<Response<DocResponse>> = MutableLiveData()
    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.postLogin(email,password)
            LoginResponse.value = response
        }
    }

    fun getPersonalDetails(uid : String?){
        viewModelScope.launch {
            val response = repository.getPersonalDetails(uid)
            PersonalDeatailResponse.value = response
        }
    }

    fun getDocDetails(uid : String?){
        viewModelScope.launch {
            val response = repository.getDocDetails(uid)
            DocDeatailResponse.value = response
        }
    }
//    fun postLogin2(post : LoginInput) {
//        viewModelScope.launch {
//            val response = repository.postLogin2(post)
//            LoginResponse.value = response
//        }
//    }
}