package com.sample.app.smarttasks.presentation.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.app.smarttasks.R
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.presentation.common.UiEvent
import com.sample.app.smarttasks.presentation.tasks.components.DayHeader
import com.sample.app.smarttasks.presentation.tasks.components.EmptyState
import com.sample.app.smarttasks.presentation.tasks.components.TaskItem
import com.sample.app.smarttasks.presentation.tasks.data.TasksState
import com.sample.app.smarttasks.utils.Helper

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate<Task>) -> Unit,
) {
    val state = viewModel.tasksState
    TasksScreen(
        modifier = modifier,
        state = state,
        onNavigate = onNavigate,
        goToNextDay = viewModel::goToNextDay,
        goToPreviousDay = viewModel::goToPreviousDay,
    )
}

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    state: TasksState,
    onNavigate: (UiEvent.Navigate<Task>) -> Unit,
    goToNextDay: () -> Unit,
    goToPreviousDay: () -> Unit,
) {

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error.isNotEmpty() -> {
                Text(text = stringResource(R.string.error_in_getting_data))
            }

            state.emptyState -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    DayHeader(state, goToPreviousDay, goToNextDay)
                    EmptyState()
                }
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState()) {
                    item {
                        DayHeader(state, goToPreviousDay, goToNextDay)
                    }
                    items(state.tasks, key = { it.id }) { item ->
                        TaskItem(
                            title = item.title,
                            date = item.dueDate ?: "",
                            dayLeft = item.dayLeft,
                            taskState = item.isResolved ?: false,
                        ) {
                            onNavigate(UiEvent.Navigate(item))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61)
@Composable
fun TasksScreenPreview() {
    TasksScreen(state = TasksState(tasks = Helper.taskList), onNavigate = ({}), goToNextDay = { }) {

    }
}
