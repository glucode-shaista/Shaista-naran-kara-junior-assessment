package com.example.todoapp.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.Priority
import com.example.todoapp.data.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.todoapp.viewmodel.WeatherViewModel
import com.example.todoapp.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.withIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    weatherViewModel: WeatherViewModel,
    onEditClick: (Task) -> Unit,
    onAddClick: () -> Unit
) {
    //val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    //val isLoading by remember { derivedStateOf { tasks.isEmpty() } }

    val activeTasks by viewModel.incompleteTasks.collectAsState(initial = emptyList())
    val completedTasks by viewModel.completedTasks.collectAsState(initial = emptyList())

    TaskListContent(
        activeTasks = activeTasks,
        completedTasks = completedTasks,
        weatherViewModel = weatherViewModel,
        onCheckedChange = { task, isChecked ->
            viewModel.updateTask(task.copy(isCompleted = isChecked))
        },
        onFavoriteClick = { task ->
            viewModel.updateTask(task.copy(favorite = !task.favorite))
        },
        onDeleteClick = { task ->
            viewModel.deleteTask(task)
        },
        onEditClick = onEditClick,
        onAddClick = onAddClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    activeTasks: List<Task>,
    completedTasks: List<Task>,
    weatherViewModel: WeatherViewModel?,
    onCheckedChange: (Task, Boolean) -> Unit = {_, _ ->},
    onFavoriteClick: (Task) -> Unit = {},
    onDeleteClick: (Task) -> Unit = {},
    onEditClick: (Task) -> Unit = {},
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "My Tasks",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF42A5F5)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF42A5F5),
                contentColor = Color.White
            ) {
             Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            weatherViewModel?.let {
                item {
                    WeatherScreen(weatherViewModel = it)
                    Spacer(modifier = Modifier.height(16.dp))
                }

        }

            if (activeTasks.isNotEmpty()) {
                item {
                    Text(
                        text = "To Do Tasks",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF0252CB),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                items(activeTasks) { task ->
                    TaskItem(
                        task = task,
                        onCheckedChange = { isChecked -> onCheckedChange(task, isChecked) },
                        onFavoriteClick = { onFavoriteClick(task) },
                        onDeleteClick = { onDeleteClick(task) },
                        onEditClick = { onEditClick(task) }
                    )
                }
            }
            if (completedTasks.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Completed Tasks",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                items(completedTasks) { task ->
                    TaskItem(
                        task = task,
                        onCheckedChange = { isChecked -> onCheckedChange(task, isChecked) },
                        onFavoriteClick = { onFavoriteClick(task) },
                        onDeleteClick = { onDeleteClick(task) },
                        onEditClick = { onEditClick(task) }
                    )
                }
            }

            if (activeTasks.isEmpty() && completedTasks.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No Tasks yet. Start Adding one.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListContentPreview() {
    val sampleActiveTasks = listOf(
        Task(
            id = 1,
            title = "Do washing",
            description = "All white items",
            isCompleted = false,
            favorite = true,
            currentDateTime = java.time.LocalDateTime.now(),
            priority = Priority.Low
        )
    )
    val sampleCompletedTasks = listOf(
        Task(
            id = 2,
            title = "Work on Compose",
            description = "Design and Dependencies",
            isCompleted = true,
            favorite = false,
            currentDateTime = java.time.LocalDateTime.now(),
            priority = Priority.Medium
        )
    )


    TaskListContent(
        activeTasks = sampleActiveTasks,
        completedTasks = sampleCompletedTasks,
        weatherViewModel = null,
        onCheckedChange = {_, _->},
        onFavoriteClick = {},
        onDeleteClick = {},
        onEditClick = {},
        onAddClick = {}
    )

}