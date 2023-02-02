package com.example.noteappkmm.android.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteappkmm.domain.date.DateTimeUntil
import com.example.noteappkmm.domain.note.Note

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteItemComponent(
    note:Note,
    deleteCallback:(Long) -> Unit,
    noteClickCallback:() -> Unit,
    background:Color,
    modifier: Modifier = Modifier
) {

    val formattedDate = remember{
        DateTimeUntil.formatNoteDate(note.created)
    }
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            noteClickCallback()
        },
        backgroundColor = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        deleteCallback(note.id!!)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = note.content,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = formattedDate,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }


}