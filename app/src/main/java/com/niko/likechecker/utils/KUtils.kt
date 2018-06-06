package com.niko.likechecker.utils

import com.vk.sdk.api.model.VKApiPhoto
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.functions.BiFunction
import java.util.*
import java.util.concurrent.TimeUnit.*

fun <T> Observable<T>.delaySecond(second: Long): Observable<T> {
    return this.concatMap { just(it).delay(second, SECONDS) }
}

val margeImagesFunc = BiFunction<List<VKApiPhoto>, List<VKApiPhoto>, List<VKApiPhoto>> { vkApiPhotos1, vkApiPhotos2 ->
    val photoList = ArrayList<VKApiPhoto>()
    photoList.addAll(vkApiPhotos1)
    photoList.addAll(vkApiPhotos2)
    photoList
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
