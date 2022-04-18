package com.radwaelsahn.currencyapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Radwa Elsahn on 7/7/2020
 */

class Network {

    companion object Utils {
        private fun getNetworkInfo(context: Context): NetworkInfo? {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        }

        fun isConnected(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected
        }
    }
}
