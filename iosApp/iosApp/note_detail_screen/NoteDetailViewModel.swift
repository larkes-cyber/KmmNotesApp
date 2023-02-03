//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by MacBook on 03.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteDetailScreen {
    
    @MainActor class NoteDetailViewModel:ObservableObject{
        private var noteDataSource:NoteDataSource?
        
        private var noteId:Int64? = nil
        @Published  var noteTitle = ""
        @Published  var noteContent = ""
        @Published private(set) var noteColor = Note.Companion().generateRandom()
        
        init(noteDataSource: NoteDataSource?) {
            self.noteDataSource = noteDataSource
        }
        
        func loadNoteIfExists(id:Int64?){
            if id != nil {
                self.noteId = id
                noteDataSource?.getNoteById(id: id!, completionHandler: { note, error in
                    self.noteTitle = note?.title ?? ""
                    self.noteContent = note?.content ?? ""
                    self.noteColor = note?.colorHex ?? Note.Companion().generateRandom()
                })
            }
        }
        
        func saveNote(onSaved:@escaping () -> Void){
            noteDataSource?.insertNote(
                note: Note(id: noteId == nil ? nil : KotlinLong(value: noteId!), title: noteTitle, content: noteContent, colorHex: noteColor, created: DateTimeUntil().now()), completionHandler: {err in
                    onSaved()
            })
        }
        
        func setParams(noteDataSource:NoteDataSource, noteId:Int64?){
            self.noteDataSource = noteDataSource
            loadNoteIfExists(id: noteId)
        }
        
    }
    
}
