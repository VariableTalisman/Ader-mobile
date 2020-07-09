package org.jtom.ader_mobile.util

import android.content.Context
import android.content.SharedPreferences
import org.jtom.ader_mobile.R

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager (context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object : SingletonHolder<SessionManager, Context>(::SessionManager) {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save the auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch the auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to remove the auth token
     */
    fun removeToken() {
        prefs.edit().remove(USER_TOKEN).apply()
    }
}
