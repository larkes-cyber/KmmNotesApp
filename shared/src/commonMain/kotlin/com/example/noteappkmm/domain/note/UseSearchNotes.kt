package com.example.noteappkmm.domain.note

import com.example.noteappkmm.domain.date.DateTimeUntil

class UseSearchNotes {

    fun execute(notes:List<Note>, query:String):List<Note>{

        if(query.isBlank())return notes

        return notes.filter {
            it.title.trim().lowercase().contains(query)
                    || it.content.trim().lowercase().contains(query)
        }.sortedBy {
            DateTimeUntil.toEpochMillis(it.created)
        }

    }

}