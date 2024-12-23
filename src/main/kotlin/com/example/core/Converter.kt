package com.example.core

import java.math.BigInteger
import java.math.BigInteger.ZERO

const val GENERIC_INVALID_MESSAGE = "Please correct the time format (HHhMM). Examples: 01h30, 1h30, 1h, 00h30, 0h15 etc"

fun String?.toSeconds(): BigInteger {
    if(this.isNullOrEmpty()) throw IllegalArgumentException("Please fill the time field")
    var hours: BigInteger
    var minutes: Int

    if(!this.contains("h")) {
        throw IllegalArgumentException(GENERIC_INVALID_MESSAGE)
    }

    try {
        val hoursAndMinutes = this.toHoursAndMinutes()
        val hoursStr = hoursAndMinutes[0]
        val minutesStr = hoursAndMinutes[1]

        hours = hoursStr.toBigInteger()
        minutes = minutesStr.toInt()
    } catch (e: Exception) {
        throw IllegalArgumentException(GENERIC_INVALID_MESSAGE)
    }

    if (hours < BigInteger.ZERO || minutes < 0) {
        throw IllegalArgumentException("There should be no negative value")
    }
    if(hours == ZERO && minutes == 0) {
        throw IllegalArgumentException("Just use your power button to instantly turn your computer off")
    }
    if(minutes > 59) {
        throw IllegalArgumentException("Minutes can not surpass 59")
    }

    return hours.times(3600.big()) + (minutes * 60).big()
}

private fun Int.big() = BigInteger.valueOf(this.toLong())

fun String.toHoursAndMinutes() = this
    .split("h")
    .map { it.ifEmpty { "0" } }