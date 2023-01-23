package com.example.noteappkmm.domain.note

import database.NoteEntity

interface NoteDataSource {

    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id:Long):Note?
    suspend fun getAllNotes():List<Note>
    suspend fun deleteNote(id:Long)

}