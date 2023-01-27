package com.example.noteappkmm.android.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteSearchTextFieldComponent(
    text:String,
    isSearchActive:Boolean,
    onSearchClick:() -> Unit,
    onCloseClick:() -> Unit,
    onTextChange:(String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.padding(end = 70.dp, start = 20.dp, top = 20.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                shape = RoundedCornerShape(50.dp),
                placeholder = { Text(text = "Search..")},
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            Modifier.align(Alignment.CenterEnd)
        ) {
            AnimatedVisibility(
                visible = !isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = {
                    onSearchClick()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
            AnimatedVisibility(
                visible = isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = {
                    onCloseClick()
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            }
        }

    }



}