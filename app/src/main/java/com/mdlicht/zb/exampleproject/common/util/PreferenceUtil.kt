package com.mdlicht.zb.exampleproject.common.util

import android.content.Context
import android.preference.PreferenceManager


object PreferenceUtil {
    fun readPref(context: Context, preferenceName: String, defaultValue: Boolean): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(preferenceName, defaultValue)
    }

    fun savePref(context: Context, preferenceName: String, preferenceValue: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(context: Context, preferenceName: String, defaultValue: Int): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(preferenceName, defaultValue)
    }

    fun savePref(context: Context, preferenceName: String, preferenceValue: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putInt(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(context: Context, preferenceName: String, defaultValue: String): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(preferenceName, defaultValue)
    }

    fun savePref(context: Context, preferenceName: String, preferenceValue: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(context: Context, preferenceName: String, defaultValue: Long): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getLong(preferenceName, defaultValue)
    }

    fun savePref(context: Context, preferenceName: String, preferenceValue: Long) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putLong(preferenceName, preferenceValue)
        editor.apply()
    }

    fun readPref(context: Context, preferenceName: String, defaultValue: Float): Float {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getFloat(preferenceName, defaultValue)
    }

    fun savePref(context: Context, preferenceName: String, preferenceValue: Float) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putFloat(preferenceName, preferenceValue)
        editor.apply()
    }

    fun getMap(context: Context): MutableMap<String, *>? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.all
    }

    fun clear(context: Context) {
        // 모든 SharedPreference 초기화
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}