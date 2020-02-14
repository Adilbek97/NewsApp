package com.example.newsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.newsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    val isNetworkConnected: Boolean
        get() {
            val cm =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }

    companion object {
        lateinit var instance: MyApplication
            private set

        fun hasNetwork(): Boolean {
            return instance.isNetworkConnected
        }
    }
}