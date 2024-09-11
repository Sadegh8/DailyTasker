package com.sample.app.smarttasks.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sample.app.smarttasks.R

class TaskCommentDialog {

    @Composable
    fun ShowDialog(
        showDialog: MutableState<Boolean>,
        initialComment: String?,
        onDone: (String?) -> Unit,
        onCancel: () -> Unit
    ) {
        var comment by remember(initialComment) { mutableStateOf(TextFieldValue(initialComment ?: "")) }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(stringResource(R.string.change_task_state)) },
                text = {
                    Column {
                        Text(stringResource(R.string.please_leave_a_comment_if_needed))
                        Spacer(modifier = Modifier.height(16.dp))
                        BasicTextField(
                            value = comment,
                            onValueChange = { comment = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            decorationBox = { innerTextField ->
                                if (comment.text.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.leave_a_comment),
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDone(comment.text.ifEmpty { null })
                            showDialog.value = false
                        }
                    ) {
                        Text(stringResource(R.string.done))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onCancel()
                            showDialog.value = false
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}
