package com.sinxn.myhabits.util.settings

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.sinxn.myhabits.R

enum class Interval(@StringRes val title: Int) {
    DAILY(R.string.daily),
    WEEKLY(R.string.weekly),
    MONTHLY(R.string.monthly)
}

fun Int.toInterval(): Interval {
    return when (this) {
        0 -> Interval.DAILY
        1 -> Interval.WEEKLY
        2 -> Interval.MONTHLY
        else -> Interval.DAILY
    }
}

fun Interval.toInt(): Int {
    return when (this) {
        Interval.DAILY -> 0
        Interval.WEEKLY -> 1
        Interval.MONTHLY -> 2
    }
}