package az.red.data.local

import android.content.Context
import android.content.SharedPreferences
import az.red.data.util.JwtParser

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        const val NAME = "az.red.commerce"
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"
        const val CURRENT_LANGUAGE = "current_language"
        const val DARK_MODE = "dark_mode"
        const val REMEMBER_ME = "remember_me"
    }

    // Language
    fun saveCurrentLanguage(language: String) {
        val editor = prefs.edit()
        editor.putString(CURRENT_LANGUAGE, language)
        editor.apply()
    }

    fun getCurrentLanguage(): String {
        val language = prefs.getString(CURRENT_LANGUAGE, "az")
        return language.toString()
    }

    // Theme Mode
    fun saveDarkMode(isDarkMode: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(DARK_MODE, isDarkMode)
        editor.apply()
    }

    // Token
    fun saveAuthToken(token: String,  rememberMe: Boolean) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_ID, JwtParser.getUserId(token))
        editor.putBoolean(REMEMBER_ME, rememberMe)
        editor.apply()
    }

    fun removeAuthToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove(USER_ID)
        editor.remove(REMEMBER_ME)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun getCurrentAppTheme() : Boolean{
        return prefs.getBoolean(DARK_MODE,false)
    }


    // User id
    fun getUserId(): String? {
        return prefs.getString(USER_ID, null)
    }

    // Remember me
    fun getRememberMe(): Boolean {
        return prefs.getBoolean(REMEMBER_ME, false)
    }
}