package com.sample.app.smarttasks.utils

import com.sample.app.smarttasks.data.common.Constants
import com.sample.app.smarttasks.domain.model.Task
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

object Helper {

    fun daysLeft(targetDate: String, dueDate: String?): String {
        if (dueDate.isNullOrEmpty()) {
            return "Due date is missing"
        }

        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())

        val target = dateFormat.parse(targetDate)
        val due = dateFormat.parse(dueDate)

        if (target == null || due == null) {
            return "Invalid date format"
        }

        val targetCalendar = Calendar.getInstance().apply {
            time = target
        }

        val dueCalendar = Calendar.getInstance().apply {
            time = due
        }

        val diffInMillis = dueCalendar.timeInMillis - targetCalendar.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(diffInMillis).toString()
    }


    fun parseDate(dateString: String?): LocalDate? {
        return try {
            dateString?.let { LocalDate.parse(it, DateTimeFormatter.ISO_DATE) }
        } catch (e: DateTimeParseException) {
            null
        }
    }

    val taskList = listOf(
        Task(
            id = "be06c9b6b02a499daa6f4a9bc12d6d43",
            targetDate = "2024-08-25",
            dueDate = "2024-08-31",
            title = "SDK update",
            dayLeft = "6 days left",
            description = "Update Android SDK to the latest version. More info at https://developer.android.com/studio/intro/update",
            priority = "High",
            isResolved = false
        ),
        Task(
            id = "d4fe42a94af24fce8beccbed9fffa7a2",
            targetDate = "2024-08-25",
            dueDate = "2024-09-10",
            title = "Setup Jenkins",
            dayLeft = "15 days left",
            description = "Setup Jenkins environment for SomeCoolApp. Feel free to ask Jeff for help (jeffthemighty@example.com)",
            priority = "Medium",
            isResolved = false
        ),
    )
}
