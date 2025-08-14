package com.example.taskv4.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PriorityBadge(priority: String) {
    val priorityColor = when (priority.lowercase()) {
        "high" -> Color(0xFFEF4444) // Red-400 equivalent
        "medium" -> Color(0xFFA855F7) // Purple-400 equivalent
        "low" -> Color(0xFF60A5FA) // Blue-400 equivalent
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val priorityIcon = when (priority.lowercase()) {
        "high" -> "! ! !"
        "medium" -> "! !"
        "low" -> "!"
        else -> ""
    }

    Icon(
        imageVector = Icons.Outlined.Flag,
        contentDescription = "Priority",
        modifier = Modifier,
        tint = priorityColor
    )
}

@Preview(showBackground = true)
@Composable
fun PriorityBadgePreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PriorityBadge(priority = "High")
            PriorityBadge(priority = "Medium")
            PriorityBadge(priority = "Low")
            PriorityBadge(priority = "Unknown")
        }
    }
}