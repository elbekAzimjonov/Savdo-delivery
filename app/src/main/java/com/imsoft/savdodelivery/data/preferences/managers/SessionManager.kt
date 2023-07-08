package com.imsoft.savdodelivery.data.preferences.managers

import android.content.Context
import android.content.SharedPreferences
import com.imsoft.savdodelivery.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
    }
    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }
    /**
     * Function to save user ID
     */
    fun saveUserID(id: String?) {
        val editor = prefs.edit()
        editor.putString(USER_ID, id)
        editor.apply()
    }
    /**
     * Function to fetch user ID
     */
    fun fetchUserId(): String? {
        return prefs.getString(USER_ID, null)
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}