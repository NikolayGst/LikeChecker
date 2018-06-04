package com.niko.likechecker.utils

import java.util.*


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
