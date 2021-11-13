package com.unsplash.data

import com.unsplash.api.ApiService
import com.unsplash.di.NetworkModule.LOGIN_URL
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: ApiService
) {

    suspend fun login() {
        val params: MutableMap<String, String> = HashMap()
        params["username"] = "demo@demo.com"
        params["password"] = "demo123"

        service.loginService(LOGIN_URL,params)
    }


}