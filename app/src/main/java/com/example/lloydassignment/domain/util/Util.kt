package com.example.lloydassignment.domain.util

import com.example.lloydassignment.domain.constants.DomainConstants.DAYS_AGO
import com.example.lloydassignment.domain.constants.DomainConstants.TODAY
import com.example.lloydassignment.domain.constants.DomainConstants.YESTERDAY
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import java.util.UUID
import kotlin.math.abs

/**
 * This function converts date string to a formatted date string.
 *
 * This function transforms a date string to a formatted string indicating
 * the relative time Today, Yesterday, X days ago".
 *
 * @receiver Date String to be formatted.
 * @return String indicating the relative time.
 */
fun String.toFormattedDate(): String {
    val days = Clock.System.todayIn(TimeZone.currentSystemDefault()).daysUntil(
        Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault()).date
    )
    //Returning formatted date string
    return when {
        abs(days) > 1 -> "${abs(days)} $DAYS_AGO"
        abs(days) == 1 -> YESTERDAY
        else -> TODAY
    }
}

/**
 * This function generates a unique identifier string.
 * @return String.
 */
fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}