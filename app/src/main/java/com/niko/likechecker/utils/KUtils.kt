package com.niko.likechecker.utils

import io.reactivex.Observable
import io.reactivex.Observable.just
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

fun <T> Observable<T>.delaySecond(second: Long): Observable<T> {
    return this.concatMap { just(it).delay(second, SECONDS) }
}

fun <T> Observable<T>.delayMillisecond(millis: Long): Observable<T> {
    return this.concatMap { just(it).delay(millis, MILLISECONDS) }
}

fun Long.formatTime(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(Date(this))

}

fun getMonth(): Long {
    val d = Date()
    val dateBefore = Date(d.time - 30 * 24 * 3600 * 1000L)
    return dateBefore.time
}

fun getWeek(): Long {
    val d = Date()
    val dateBefore = Date(d.time - 7 * 24 * 3600 * 1000L)
    return dateBefore.time
}
