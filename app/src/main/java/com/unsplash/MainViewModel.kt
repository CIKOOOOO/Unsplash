package com.unsplash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {

//    var text: String = "Selamat datang, silahkan login untuk mengguanakan fitur aplikasi ini!"
//    var user: User? = User(username = "user", password = "pass")
//
//
//    //region login
//    var loginInfo = MutableLiveData<LoginInfo>(LoginInfo(false, ""))
//    fun login(){
//        if (user?.username.equals("user") && user?.password.equals("pass")){
//            val message = "login success, " +
//                    "data cointain user ${user?.username}, passwprd as ${user?.password}"
//            val result = LoginInfo(true, message)
//            loginInfo.value = result
//
//        } else {
//            val message = "login fail, " +
//                    "data cointain user ${user?.username}, password as ${user?.password}"
//            val result = LoginInfo(false, message)
//            loginInfo.value = result
//        }
//    }
    //endregion

    //region cek saldo
    var saldo: String? = "0.0"
    //endregion

    // region fragment transfer
    var transferAmount: String? = "0"
    var cancelMessage = MutableLiveData<String>()
    var nextMessage = MutableLiveData<String>()
    fun cancel(){
        cancelMessage.postValue("cancel function execute with param ${transferAmount}")
    }

    fun next(){
        nextMessage.postValue("cancel function execute with param ${transferAmount}")
    }
    // endregion

    //region day 3
    var day3Argument: String = ""
    //endregion
}