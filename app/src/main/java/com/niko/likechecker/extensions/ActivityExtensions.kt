package com.niko.likechecker.extensions

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

val debug = true

private val tag = "LikeChecker"

fun logs(message: String) {
    if (debug) Log.d(tag, message)
}

fun AppCompatActivity.showDialog(dialogFragment: DialogFragment, tag: String = "default_dialog") {
    dialogFragment.show(supportFragmentManager, tag)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()
fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(context, message, duration).show()