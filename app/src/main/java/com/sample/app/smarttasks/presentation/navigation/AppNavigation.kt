package com.sample.app.smarttasks.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.presentation.detail.DetailScreen
import com.sample.app.smarttasks.presentation.tasks.TasksScreen

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
    topBarState: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Tasks,
        modifier = modifier
    ) {
        addTasks(
            padding = padding,
            navController = navController,
            topBarState = topBarState
        )
        addDetails(
            padding = padding,
            topBarState = topBarState
        )
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addTasks(
    padding: PaddingValues,
    navController: NavHostController,
    topBarState: MutableState<Boolean>
) {
    composable<Tasks>
    {
        // Hide top bar when navigating to tasks
        LaunchedEffect(Unit) {
            topBarState.value = false
        }
        TasksScreen(modifier = Modifier.padding(padding)) {
            navController.navigate(it.route)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDetails(
    padding: PaddingValues,
    topBarState: MutableState<Boolean>
) {
    composable<Task>
    {
        LaunchedEffect(Unit) {
            topBarState.value = true
        }
        val args = it.toRoute<Task>()
        DetailScreen(modifier = Modifier.padding(padding), task = args)
    }
}
