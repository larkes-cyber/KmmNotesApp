package com.example.noteappkmm.domain.date

import kotlinx.datetime.*

object DateTimeUntil {

    fun now():LocalDateTime{
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMillis(dateTime: LocalDateTime):Long{
        return dateTime.toInstant(TimeZone.currentSystemDefault()).epochSeconds
    }

    fun formatNoteDate(dateTime: LocalDateTime):String{
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if(dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val hour = if(dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val minutes = if(dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute
        val year = dateTime.year

        return "$month $day $year, $hour:$minutes"
    }

}