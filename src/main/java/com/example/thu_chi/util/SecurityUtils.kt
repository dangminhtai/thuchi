package com.example.thu_chi.util

import android.content.Context

object SecurityUtils {
    private const val PREF_NAME = "security_prefs"
    private const val KEY_PIN = "app_pin"

    var isAuthenticated = false

    fun setPin(context: Context, pin: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PIN, pin).apply()
    }

    fun getPin(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PIN, null)
    }

    fun isLocked(context: Context): Boolean {
        return getPin(context) != null
    }
}
