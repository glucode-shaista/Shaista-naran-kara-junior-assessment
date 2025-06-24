package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.google.android.gms.location.Priority
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import com.example.todoapp.data.Priority

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val currentDateTime: LocalDateTime? = LocalDateTime.now(),
    //val time: LocalTime,
    //val date:LocalDate,
    val isCompleted: Boolean = false,
    val favorite: Boolean = false,
    val priority: Priority = Priority.Low

)
