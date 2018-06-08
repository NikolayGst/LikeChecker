package com.niko.likechecker.extensions

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun logs(message: String, tag: String = "LikeChecker", debug: Boolean = false) {
    if (debug) Log.d(tag, message)
}

fun postExecute(time: Long = 4000, body: () -> Unit) : Disposable {
    return Observable.timer(time, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .map { body() }
            .subscribe()
}


fun AppCompatActivity.showDialog(dialogFragment: DialogFragment, tag: String = "default_dialog") {
    dialogFragment.show(supportFragmentManager, tag)
}

fun EditText.searchObservable(): Observable<String> {
    return Observable.create {

        val value = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                it.onNext(s.toString())
            }
        }

        this.addTextChangedListener(value)

        it.setCancellable { this.removeTextChangedListener(value) }
    }
}


fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()
fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(context, message, duration).show()