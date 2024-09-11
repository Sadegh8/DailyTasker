package com.sample.app.smarttasks.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sample.app.smarttasks.presentation.common.TopBar
import com.sample.app.smarttasks.presentation.navigation.AppNavigation
import com.sample.app.smarttasks.ui.theme.SmartTasksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartTasksTheme {
                val navController = rememberNavController()
                val topBarState = rememberSaveable { (mutableStateOf(true)) }

                Scaffold(
                    topBar = {
                        AnimatedVisibility(
                            visible = topBarState.value,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                        ) {
                            TopBar(
                                Modifier,
                                navController,
                            )

                        }
                    },

                    ) { padding ->

                    AppNavigation(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        padding = padding,
                        topBarState = topBarState
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartTasksTheme {
        Greeting("Android")
    }
}
