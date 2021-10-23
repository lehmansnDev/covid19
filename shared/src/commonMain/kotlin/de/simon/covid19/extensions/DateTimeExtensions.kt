package de.simon.covid19.extensions

import kotlinx.datetime.*

fun LocalDateTime.isInSameHour(date: LocalDateTime): Boolean {
    return this.year == date.year &&
            this.month == date.month &&
            this.dayOfMonth == date.dayOfMonth &&
            this.hour == date.hour
}

fun LocalDateTime.minus(dateTimePeriod: DateTimePeriod, timeZone: TimeZone): LocalDateTime {
    return this.toInstant(timeZone)
        .minus(dateTimePeriod, timeZone)
        .toLocalDateTime(timeZone)
}