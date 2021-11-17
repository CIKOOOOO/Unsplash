package com.unsplash.data

import android.util.Base64
import android.util.Log
import com.unsplash.api.ApiService
import com.unsplash.di.NetworkModule.LOGIN_URL
import com.unsplash.utils.SharedPreferenceUtil
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.MutableMap
import kotlin.collections.set

class LoginRepository @Inject constructor(
    private val service: ApiService
) {

    @Inject
    lateinit var sharedPref: SharedPreferenceUtil

    suspend fun login(userName: String, password: String): String {
        val params: MutableMap<String, String> = HashMap()
        params["username"] = userName
        params["password"] = password

        try {
            val response = service.loginService(LOGIN_URL, params)
            Log.d("GLG", response.code().toString())
            return when (response.code()) {
                200 -> {
                    val loginResponse = response.body()
                    Log.d("GLG", response.body().toString())
                    if (loginResponse != null) {
                        encryptAndSaveToken(loginResponse.token)
                    }
                    "success"
                }
                403 -> "Invalid API key"
                406 -> "Username or Password Invalid"
                else -> "Oops something when wrong, please try again."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Oops something when wrong, please try again."
        }
    }

    private fun encryptAndSaveToken(token: String) {

        val encryptedToken: String = encryptString(token)

        sharedPref.putString("token", encryptedToken)

        val tokenFromSharedPref = sharedPref.getString("token", encryptedToken)
        Log.d("GLG saved token", tokenFromSharedPref.toString())

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