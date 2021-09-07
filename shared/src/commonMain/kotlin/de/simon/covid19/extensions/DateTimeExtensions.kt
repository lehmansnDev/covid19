package de.simon.covid19.extensions

import java.time.LocalDateTime

fun LocalDateTime.isInSameHour(date: LocalDateTime): Boolean {
    return this.getYear() == date.getYear() &&
            this.getMonth() == date.getMonth() &&
            this.getDayOfMonth() == date.getDayOfMonth() &&
            this.getHour() == date.getHour()
}