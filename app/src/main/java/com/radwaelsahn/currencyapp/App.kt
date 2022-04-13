package com.radwaelsahn.currencyapp


import android.app.Application
import android.content.Context
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
open class App : Application() {

    val TAG = this.javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}
