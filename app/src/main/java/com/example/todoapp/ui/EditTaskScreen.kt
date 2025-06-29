package com.example.todoapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.Task
import com.example.todoapp.data.Priority
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    task: Task,
    onSaveClick: (Task) -> Unit,
    onCancelClick: () -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var priority by remember { mutableStateOf(task.priority ?: Priority.Low) }
    var expanded by remember { mutableStateOf(false) }
    //val task = remember(taskViewModel.allTasks.value) {
    //taskViewModel.getTaskById(taskId)
    //}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Task") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF90CAF9),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Card(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Update Task Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1976D2)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = priority.name,
                        onValueChange = {},
                        label = { Text("Priority") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Priority.values().forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level.name) },
                                onClick = {
                                    priority = level
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = onCancelClick) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        onSaveClick(
                            task.copy(
                                title = title.trim(),
                                description = description.trim(),
                                priority = priority,
                                currentDateTime = LocalDateTime.now()
                            )
                        )
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview(){
    val sampleTask = Task(
            id = 1,
            title = "Do washing",
            description = "All white items",
            isCompleted = false,
            favorite = true,
            currentDateTime = java.time.LocalDateTime.now(),
            priority = Priority.Medium
    )
    EditTaskScreen(
        task = sampleTask,
        onSaveClick = {},
        onCancelClick = {}
    )
}
