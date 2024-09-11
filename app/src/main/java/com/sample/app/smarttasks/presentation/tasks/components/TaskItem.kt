package com.sample.app.smarttasks.presentation.tasks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.app.smarttasks.R
import com.sample.app.smarttasks.ui.theme.CardColor
import com.sample.app.smarttasks.ui.theme.TitleColor

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    dayLeft: String,
    taskState: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TitleColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Image(
                    modifier = Modifier
                        .size(15.dp),
                    painter = if (taskState) painterResource(id = R.drawable.sign_resolved) else painterResource(
                        id = R.drawable.unresolved_sign
                    ),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = stringResource(R.string.due_date),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(R.string.days_left),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = date, style = MaterialTheme.typography.titleLarge,
                    color = TitleColor, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = dayLeft, style = MaterialTheme.typography.titleLarge,
                    color = TitleColor, fontWeight = FontWeight.Bold
                )

            }
        }

    }

}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFDE61)
@Composable
fun TaskItemPreview() {
    TaskItem(title = "Task Title", date = "Apr 22, 2024", dayLeft = "12") {}

}
