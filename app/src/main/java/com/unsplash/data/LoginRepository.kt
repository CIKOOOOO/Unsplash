package com.unsplash.data

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import android.widget.Switch
import com.unsplash.api.ApiService
import com.unsplash.di.NetworkModule.LOGIN_URL
import com.unsplash.utils.SharedPreferenceUtil
import dagger.hilt.EntryPoint
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import retrofit2.HttpException
import java.lang.reflect.Array.get
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val service: ApiService
) {

    @Inject lateinit var sharedPref: SharedPreferenceUtil

    suspend fun login(userName: String, password: String): String {
        val params: MutableMap<String, String> = HashMap()
        params["username"] = userName
        params["password"] = password

        val response = service.loginService(LOGIN_URL, params)

        try {
            if (response.isSuccessful) {
                Log.d("GLG", response.code().toString())
                return when (response.code()) {
                    200 -> {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            encryptAndSaveToken(loginResponse.token)
                        }
                        "success"
                    }
                    403 -> "Invalid API key"
                    406 -> "Username or Password Invalid"
                    else -> "Oops something when wrong, please try again."
                }
            } else {
                return "Oops something when wrong, please try again."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Oops something when wrong, please try again."
        }
    }

    private fun encryptAndSaveToken(token: String) {

        var encryptedToken: String = encryptString(token)

        sharedPref.putString("token",encryptedToken)

        var tokenFromSharedPref = sharedPref.getString("token",encryptedToken)
        Log.d("GLG saved token",tokenFromSharedPref.toString())

    }

    fun encryptString(password: String): String {
        val plaintext: ByteArray = password.toByteArray()
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key: SecretKey = keygen.generateKey()
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val ciphertext: ByteArray = cipher.doFinal(plaintext)
        val iv: ByteArray = cipher.iv
        return Base64.encodeToString(ciphertext, Base64.DEFAULT)
    }


}