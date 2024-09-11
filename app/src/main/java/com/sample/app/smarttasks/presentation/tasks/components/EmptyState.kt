package com.sample.app.smarttasks.presentation.tasks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.app.smarttasks.R

@Composable
fun EmptyState() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier,
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.empty_screen),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.no_tasks_for_today),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61)
@Composable
fun EmptyStatePreview() {
    EmptyState()
}
