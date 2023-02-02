package com.example.noteappkmm.data.di

import com.example.noteappkmm.data.local.DatabaseDriverFactory
import com.example.noteappkmm.data.note.SqlDelightNoteDataSource
import com.example.noteappkmm.database.NoteDatabase
import com.example.noteappkmm.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy {DatabaseDriverFactory()}
    val noteDataSource:NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }

}