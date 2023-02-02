//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by MacBook on 01.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NotelListScreen {
   @MainActor class NoteListScreenViewModule: ObservableObject{
       
       private var noteDataSource: NoteDataSource? = nil
       private let searchNotes = UseSearchNotes()
       
       private var notes = [Note]()
       @Published private(set) var filtredNotes = [Note]()
       @Published var searchText = "" {
           didSet{
               self.filtredNotes = searchNotes.execute(notes: self.notes, query: searchText)
           }
       }
       @Published private(set) var isSearchActive = false
       
       init(noteDataSource:NoteDataSource? = nil){
           self.noteDataSource = noteDataSource
       }
       
       func loadNotes(){
           noteDataSource?.getAllNotes(completionHandler: {notes, error in
               self.notes = notes ?? []
               self.filtredNotes = self.filtredNotes
           })
       }
       
       func deleteNoteById(id:Int64?){
           if id != nil {
               noteDataSource?.deleteNote(id: id!, completionHandler:{ error in
                   self.loadNotes()
               })
           }
       }
       
       func toggleIsSearchActive(){
           isSearchActive = !isSearchActive
           if !isSearchActive{
               searchText = ""
           }
       }
    
       func setNoteDataSource(noteDataSource: NoteDataSource){
           self.noteDataSource = noteDataSource
           noteDataSource.insertNote(note: Note(id: nil, title: "lolka", content: "lolka", colorHex: 0xFF2355, created: DateTimeUntil().now()), completionHandler: {error in
               
           })
       }
       
    }
}
