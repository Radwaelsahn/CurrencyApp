package com.radwaelsahn.currencyapp.data.source.local

import android.util.Log
import com.google.gson.Gson
import com.radwaelsahn.currencyapp.utils.Constants
import javax.inject.Inject

// Made is as a Util Function so later on it would used whenever an Exchanging Rate is required
// Of course we can change currencyRateMapping -> fetch it from Room or the last saved CurrencyConversionList

class Session @Inject constructor(val sharedPrefHelper : SharedPrefHelper) {

    var xAccessToken: String

    private val AccessTokenKEY = "AccessToken"
    private val RefreshTokenKEY = "RefreshToken"
    val KEY_USER = "user"

    init {
        xAccessToken = getAccessToken()
    }

    fun setAccessToken(token: String?) {
        token?.let {
            sharedPrefHelper.setIntoSharedPref(AccessTokenKEY, it)
        }
        xAccessToken = token!!
    }

    fun getAccessToken(): String = sharedPrefHelper.getFromSharedPref(AccessTokenKEY).toString()


    fun logoutLocal() {
        sharedPrefHelper.removeSharedPref(KEY_USER)
        clearSession()
    }

    fun clearSession() {
        setAccessToken("")
    }
}