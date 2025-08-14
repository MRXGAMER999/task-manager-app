package com.example.taskv4.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard(
    title: String,
    priority: String,
    dueDate: String,
    isCompleted: Boolean = false,
    onCheckboxClick: (Boolean) -> Unit = { },
    onTaskClick: () -> Unit = { }
) {
    val lineColor = when (priority.lowercase()) {
        "high" -> Color(0xFFEF4444) // Red-500
        "medium" -> Color(0xFFA855F7) // Purple-500
        "low" -> Color(0xFF3B82F6) // Blue-500
        else -> Color(0xFF6B7280) // Gray-500
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onTaskClick() }
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(8f, 0f),
                    end = Offset(8f, size.height),
                    strokeWidth = 10.dp.toPx()
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    )
                    .border(
                        width = 2.dp,
                        color = if (isCompleted) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .clickable { onCheckboxClick(!isCompleted) },
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checked",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Task Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isCompleted) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else null
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Due: $dueDate",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            PriorityBadge(priority = priority)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskCard(
                title = "Complete project proposal",
                priority = "High",
                dueDate = "2025-08-20",
                isCompleted = false
            )

            TaskCard(
                title = "Review team feedback",
                priority = "Medium",
                dueDate = "2025-08-18",
                isCompleted = false
            )

            TaskCard(
                title = "Update documentation",
                priority = "Low",
                dueDate = "2025-08-25",
                isCompleted = true
            )
        }
    }
}