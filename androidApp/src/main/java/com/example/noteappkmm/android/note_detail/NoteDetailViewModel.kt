package com.example.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappkmm.domain.date.DateTimeUntil
import com.example.noteappkmm.domain.note.Note
import com.example.noteappkmm.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle","")
    private val isNoteTitleTextFocused = savedStateHandle.getStateFlow("isNoteTitleTextFocused",false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent","")
    private val isNoteContentTextFocused = savedStateHandle.getStateFlow("isNoteContentTextFocused",false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor",Note.generateRandom())

    private var existingId:Long? = null

    val state = combine(noteTitle, isNoteTitleTextFocused, noteContent, isNoteContentTextFocused, noteColor){
            noteTitle, isNoteTitleTextFocused, noteContent, isNoteContentTextFocused, noteColor ->
        NoteDetailState(
            noteTitle = noteTitle,
            isNoteTitleHintVisible = noteTitle.isEmpty() &&  !isNoteTitleTextFocused,
            isNoteContentHintVisible = noteContent.isEmpty() && ! isNoteContentTextFocused,
            noteColor = noteColor,
            noteContent = noteContent
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())


    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    init {
        savedStateHandle.get<Long>("noteId")?.let {existingNoteId ->

            if(existingNoteId == -1L) return@let

            existingId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let {note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }

            }


        }
    }

    fun onNoteTitleChanged(title:String){
        savedStateHandle["noteTitle"] = title
    }

    fun onNoteContentChanged(title:String){
        savedStateHandle["noteContent"] = title
    }

    fun onTitleFocused(isFocused:Boolean){
        savedStateHandle["isNoteTitleTextFocused"] = isFocused
    }

    fun onContentFocused(isFocused: Boolean){
        savedStateHandle["isNoteContentTextFocused"] = isFocused
    }

    fun saveNote(){
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUntil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }


}