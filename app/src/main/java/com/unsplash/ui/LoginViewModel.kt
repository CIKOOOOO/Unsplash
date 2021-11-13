package com.unsplash.ui

import androidx.lifecycle.ViewModel
import com.unsplash.data.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    suspend fun login(){
        loginRepository.login()
    }
}