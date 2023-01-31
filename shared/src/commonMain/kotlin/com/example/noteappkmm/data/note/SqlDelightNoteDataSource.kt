package com.example.noteappkmm.data.note

import com.example.noteappkmm.database.NoteDatabase
import com.example.noteappkmm.domain.date.DateTimeUntil
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import com.example.noteappkmm.domain.note.toNote
import database.NoteEntity

class SqlDelightNoteDataSource(
    db:NoteDatabase
):NoteDataSource {

     private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUntil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }

    override suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }
}