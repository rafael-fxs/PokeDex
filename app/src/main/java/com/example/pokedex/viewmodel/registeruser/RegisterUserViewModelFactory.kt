package com.example.pokedex.viewmodel.registeruser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterUserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterUserViewModel::class.java)){
            RegisterUserViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}