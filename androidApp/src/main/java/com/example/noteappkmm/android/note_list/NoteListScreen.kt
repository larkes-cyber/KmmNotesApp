package com.example.noteappkmm.android.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    viewModel:NoteListViewMode = hiltViewModel(),
    navController: NavController
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.loadNotes()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("note_detail/-1L")
                },
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NoteSearchTextFieldComponent(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onSearchClick = viewModel::onToggleSearch,
                    onCloseClick = viewModel::onToggleSearch,
                    onTextChange = viewModel::onSearchTextChange
                )
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "All notes",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 31.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(27.dp)
            ) {
                items(
                    items = state.notes,
                    key = { it.id!! }
                ) {
                    NoteItemComponent(
                        note = it,
                        deleteCallback = { id ->
                            viewModel.deleteNoteById(id)
                        },
                        noteClickCallback = {
                            navController.navigate("note_detail/${it.id}")
                        },
                        background = Color(it.colorHex),
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}