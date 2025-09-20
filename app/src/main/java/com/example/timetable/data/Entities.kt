package com.example.timetable.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

enum class DayType { WEEKDAY, WEEKEND }

@Entity(tableName = "schedule_items")
data class ScheduleItem(
    @PrimaryKey val id: String,
    val dayType: DayType,
    val start: LocalTime,
    val end: LocalTime,
    val title: String,
    val notes: String = ""
)

@Entity(tableName = "completions")
data class Completion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDate,
    val scheduleId: String,
    val done: Boolean
)