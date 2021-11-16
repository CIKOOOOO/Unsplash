package com.unsplash.utils

import android.content.Context
import androidx.preference.PreferenceManager

import android.content.SharedPreferences


class SharedPreferenceUtil(var context: Context) {

    fun getEditor(): SharedPreferences.Editor {
        return getPreferences().edit()
    }

    fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun containsPref(key: String?): Boolean {
        return getPreferences().contains(key)
    }

    fun clearPref() {
        getEditor().clear().apply()
    }

    fun clearSelectedKey(key: String?) {
        getEditor().remove(key).apply()
    }

    fun putString(key: String?, value: String?) {
        getEditor().putString(key, value).apply()
    }

    fun getString(key: String?, defValue: String?): String? {
        return getPreferences().getString(key, defValue)
    }
}