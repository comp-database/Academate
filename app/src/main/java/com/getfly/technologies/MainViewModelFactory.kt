package com.getfly.technologies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getfly.technologies.model.AcademateRepository

class MainViewModelFactory(private val repository: AcademateRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(repository) as T
    }
}