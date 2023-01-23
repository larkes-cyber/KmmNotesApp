package com.example.noteappkmm.domain.note

import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote():Note{
    return Note(
        id = id,
        title = title,
        colorHex = colorHex,
        content = content,
        created = Instant
            .fromEpochMilliseconds(created).toLocalDateTime(TimeZone.currentSystemDefault())
    )
}