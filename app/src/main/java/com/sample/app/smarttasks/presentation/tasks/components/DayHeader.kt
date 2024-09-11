package com.sample.app.smarttasks.presentation.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.app.smarttasks.presentation.tasks.data.TasksState

@Composable
fun DayHeader(
    state: TasksState,
    goToPreviousDay: () -> Unit,
    goToNextDay: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            "Left",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    if (state.dateTitle == "Today") return@clickable
                    goToPreviousDay.invoke()
                },
            tint = if (state.dateTitle == "Today") Color.LightGray else Color.White
        )
        Text(
            text = state.dateTitle,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            "Right",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    goToNextDay.invoke()
                },
            tint = Color.White
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61)
@Composable
fun DayHeaderPreview() {
    DayHeader(TasksState(dateTitle = "Today"), {}, {})
}
