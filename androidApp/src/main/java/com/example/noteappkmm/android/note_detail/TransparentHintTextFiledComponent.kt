package com.example.noteappkmm.android.note_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextFiledComponent(
    text:String,
    onTextChange:(String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    hint:String,
    modifier:Modifier = Modifier,
    textStyle:TextStyle = TextStyle(),
    isHintVisible:Boolean,
    singleLine:Boolean = false
) {
    
    Box() {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier =  modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
            ,
            textStyle = textStyle,
            singleLine = singleLine
        )
        if(isHintVisible){
            Text(
                text = hint,
                style = textStyle
            )
        }
    }

}