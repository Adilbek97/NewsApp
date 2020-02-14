package com.example.newsapp.data.network

import android.content.ContentValues.TAG
import android.util.Log
import com.example.newsapp.MyApplication
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object RetrofitService {
    private const val BASE_URL = "https://newsapi.org/"
    const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val HEADER_PRAGMA = "Pragma"
    private const val cacheSize = 5 * 1024 * 1024 // 5 MB
    //create Logger
    private val logger= HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun cache(): Cache? {
        return Cache(File(MyApplication.instance.cacheDir, "someIdentifier"), cacheSize.toLong())
    }

    private fun offlineInterceptor(): Interceptor{
        return Interceptor { chain ->
            Log.d(TAG, "offline interceptor: called.")
            var request: Request = chain.request()
            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!MyApplication.hasNetwork()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    private fun networkInterceptor(): Interceptor {
        return Interceptor { chain ->
            Log.d(TAG, "network interceptor: called.")
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.SECONDS)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor{
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    TAG,
                    "log: http log: $message"
                )
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    // create okHttp Client
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .cache(cache())
        .addInterceptor(httpLoggingInterceptor())
        .addNetworkInterceptor(networkInterceptor())
        .addNetworkInterceptor(offlineInterceptor())
        .addInterceptor(logger)

    //Create retrofit instance
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL ).addConverterFactory(
        GsonConverterFactory.create())
        .client(okHttp.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    //Create retrofit instance
    private val retrofit: Retrofit = builder.build()

    fun <T> builService(serviceType: Class<T>):T{
        return retrofit.create(serviceType)
    }
}
