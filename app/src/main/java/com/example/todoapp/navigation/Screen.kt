package com.example.todoapp.navigation

//object Routes{
   // const val TaskList = "task_list"
    //const val EditTask = "edit_task"

//}

sealed class Screen(val route: String) {
    object TaskList : Screen("task_list")
    object AddTask : Screen("add_task")
    object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: Int): String = "edit_task/$taskId"
    }
}