//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by MacBook on 02.02.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    
    @StateObject var viewModel = NoteDetailViewModel(noteDataSource: nil)
    
    private var noteDataSource:NoteDataSource
    private var noteid:Int64? = nil
    
    @Environment(\.presentationMode) var presentation
    
    init(noteDataSource: NoteDataSource, noteid: Int64? = nil) {
        self.noteDataSource = noteDataSource
        self.noteid = noteid
    }
    
    var body: some View {
        VStack{
            TextField("Enter a title...", text: $viewModel.noteTitle)
                .font(.title)
            TextField("Enter a content...", text: $viewModel.noteContent)
            Spacer()
        }.toolbar {
            Button( action: {
                viewModel.saveNote {
                    self.presentation.wrappedValue.dismiss()
                }
            }){
                Image(systemName: "checkmark")
            }
        }
        .padding()
        .background(Color(hex: viewModel.noteColor))
        .onAppear{
            viewModel.setParams(noteDataSource: noteDataSource, noteId: noteid)
        }
    }
}

struct NoteDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
