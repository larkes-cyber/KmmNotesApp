//
//  NotelListScreen.swift
//  iosApp
//
//  Created by MacBook on 02.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import shared

import SwiftUI

struct NoteListScreen: View {
    private var noteDataSource:NoteDataSource
    @StateObject var viewModel = NoteListViewModel(noteDataSource: nil)
    
    @State private var isNoteSelected = false
    @State private var selectedNoteId:Int64? = nil
    
    init(noteDataSource:NoteDataSource){
        self.noteDataSource = noteDataSource
    }
    
    
    var body: some View {
        
        VStack{
            ZStack{

                NavigationLink(destination: NoteDetailScreen(noteDataSource: noteDataSource,noteid: selectedNoteId), isActive: $isNoteSelected){
                    EmptyView()
                }.hidden()
                HideableSearchTextField<NoteDetailScreen>(onSearchToggle: {
                    viewModel.toggleIsSearchActive()
                }, destinationProvider: {
                    NoteDetailScreen(
                        noteDataSource: noteDataSource,
                        noteid: selectedNoteId
                    )
                }, isSearchActive: viewModel.isSearchActive, searchText:$viewModel.searchText)
                .frame(maxWidth:.infinity, maxHeight: 40)
                .padding()
                if !viewModel.isSearchActive {
                    Text("All notes")
                        .font(.title2)
                }
            }
            let _ = print(String(viewModel.filteredNotes.count))
            List {
                ForEach(viewModel.filteredNotes, id: \.self.id){note in
                    
                    Button(action: {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                        
                    }){
                        NoteItem(note: note, onDeleteClick: {
                            viewModel.deleteNoteById(id: note.id?.int64Value)
                        })
                    }
                }
            }.onAppear{
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }.onAppear{
            viewModel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
    
}

struct NotelListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
