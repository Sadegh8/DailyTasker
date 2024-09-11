package com.sample.app.smarttasks.presentation.detail

import com.sample.app.smarttasks.presentation.detail.components.TaskCommentDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.app.smarttasks.R
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.presentation.detail.data.DetailState
import com.sample.app.smarttasks.ui.theme.GreenColor
import com.sample.app.smarttasks.ui.theme.TitleColor


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    task: Task
) {
    val state = viewModel.detailState
    val taskStateDialog = TaskCommentDialog()

    DetailScreen(
        modifier = modifier,
        state = state,
        task = task,
        taskStateDialog = taskStateDialog,
        updateTaskState = viewModel::updateTaskState
    )
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    task: Task,
    taskStateDialog: TaskCommentDialog,
    updateTaskState: (String, Boolean, String?) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val isResolving = remember { mutableStateOf(false) }

    //Dialog
    taskStateDialog.ShowDialog(
        showDialog = showDialog,
        initialComment = state.taskComment,
        onDone = { comment ->
            updateTaskState.invoke(task.id, isResolving.value, comment)
        },
        onCancel = { showDialog.value = false }
    )

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val showGreenColor = state.taskState ?: false
        Box(contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .aspectRatio(1.1f)
                    .padding(top = 20.dp),
                painter = painterResource(id = R.drawable.task_details),
                contentDescription = "task"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1.1f)
                    .padding(top = 60.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (showGreenColor) GreenColor else TitleColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Text(
                        text = stringResource(R.string.due_date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.days_left),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = task.dueDate ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (showGreenColor) GreenColor else TitleColor,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = task.dayLeft,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (showGreenColor) GreenColor else TitleColor,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (showGreenColor) stringResource(R.string.resolved) else stringResource(
                        R.string.unresolved
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (showGreenColor) GreenColor else Color(0xFFebb734),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = state.taskComment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            }
        }
        if (state.taskState == null) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12),
                    onClick = {
                        isResolving.value = true
                        showDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color(0xFF219c35), contentColor = Color.White)
                ) {
                    Text(
                        text = stringResource(R.string.resolve),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12),
                    onClick = {
                        isResolving.value = false
                        showDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color(0xFFd94038), contentColor = Color.White)
                ) {
                    Text(
                        text = stringResource(R.string.can_t_resolve),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier = Modifier
                    .size(60.dp),
                painter = if (showGreenColor) painterResource(id = R.drawable.sign_resolved) else painterResource(
                    id = R.drawable.unresolved_sign
                ),
                contentDescription = ""
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61)
@Composable
fun DetailScreenPreview() {

    DetailScreen(
        task = Task(
            "id",
            "2024-09-14",
            "2024-09-12",
            "title",
            "4",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus in porta nunc, sed porttitor lectus. Sed dictum velit non orci fringilla tristique. Nam semper gravida consequat. Morbi id tellus nunc.",
            priority = "2"
        ),
        taskStateDialog = TaskCommentDialog(),
        state = DetailState(taskState = false),
        updateTaskState = ({ _, _, _ -> })
    )
}

@Preview(
    showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61,
    device = "id:Nexus 4"
)
@Composable
fun DetailScreenPreviewSmall() {
    DetailScreen(
        task = Task(
            "id",
            "2024-09-14",
            "2024-09-12",
            "title",
            "4",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus in porta nunc, sed porttitor lectus. Sed dictum velit non orci fringilla tristique. Nam semper gravida consequat. Morbi id tellus nunc.",
            priority = "2"
        ),
        state = DetailState(taskState = false),
        taskStateDialog = TaskCommentDialog(),
        updateTaskState = ({ _, _, _ -> })
    )
}

