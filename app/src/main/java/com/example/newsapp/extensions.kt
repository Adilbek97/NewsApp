package com.example.newsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

fun Context.showToast(message:String,duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,duration).show()
}

inline fun <reified T : Any> Activity.changeActivity() {
    startActivity(Intent(this, T::class.java))
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun View.invisible(){
    visibility = View.INVISIBLE
}
