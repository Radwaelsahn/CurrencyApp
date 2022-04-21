package com.radwaelsahn.currencyapp.data.datasources.local

import android.content.Context
import android.content.SharedPreferences
import com.radwaelsahn.currencyapp.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.KEY_PREFS, Context.MODE_PRIVATE)

    fun setIntoSharedPref(context: Context?, key: String, value: String, prefName: String) {

        val sharedPref = context?.getSharedPreferences(prefName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getFromSharedPref(context: Context?, key: String, default: String, prefName: String): String? {

        val sharedPref = context?.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        return sharedPref?.getString(key, default)
    }


    fun setIntoSharedPref(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()

    fun getFromSharedPref(key: String, default: String? = ""): String = sharedPreferences.getString(key, default) ?: ""

    fun setIntoSharedPrefLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()

    fun getFromSharedPrefLong(key: String): Long = sharedPreferences.getLong(key, 0L)

    fun clearCache() = sharedPreferences.edit()?.clear()?.apply()

    fun removeSharedPref(key: String) = sharedPreferences.edit().remove(key).apply()

    fun setIntoSharedPrefInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()

    fun getFromSharedPrefInt(key: String): Int = sharedPreferences.getInt(key, 0)



}
