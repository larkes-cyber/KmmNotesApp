package com.example.noteappkmm.android.note_detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteappkmm.android.note_list.NoteItemComponent
import com.example.noteappkmm.android.note_list.NoteSearchTextFieldComponent

@Composable
fun NoteDetailScreen(
    id:Long,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved){
        if(hasNoteBeenSaved){
            navController.popBackStack()
        }
    }

    Log.d("wefgfe",id.toString())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = " ",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(state.noteColor))
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            TransparentHintTextFiledComponent(
                text = state.noteTitle,
                onTextChange = viewModel::onNoteTitleChanged,
                onFocusChange = {
                                viewModel.onTitleFocused(it.isFocused)
                },
                hint = "Enter title here...",
                isHintVisible = state.isNoteTitleHintVisible,
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFiledComponent(
                text = state.noteContent,
                onTextChange = viewModel::onNoteContentChanged,
                onFocusChange = {
                    viewModel.onContentFocused(it.isFocused)
                },
                hint = "Enter content here...",
                isHintVisible = state.isNoteContentHintVisible,
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }

}