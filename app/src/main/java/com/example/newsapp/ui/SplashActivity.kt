package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newsapp.R
import com.example.newsapp.changeActivity
import com.example.newsapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        myHandler= Handler()
        myHandler.postDelayed({
            startBottomActivity()
        }, splashTime)
    }

    private fun startBottomActivity(){
        changeActivity<MainActivity>()
        finish()
    }

}
