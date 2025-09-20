package com.example.timetable.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter fun fromLocalTime(t: LocalTime?): String? = t?.toString()
    @TypeConverter fun toLocalTime(s: String?): LocalTime? = s?.let(LocalTime::parse)

    @TypeConverter fun fromLocalDate(d: LocalDate?): String? = d?.toString()
    @TypeConverter fun toLocalDate(s: String?): LocalDate? = s?.let(LocalDate::parse)

    @TypeConverter fun fromDayType(dt: DayType?): String? = dt?.name
    @TypeConverter fun toDayType(s: String?): DayType? = s?.let { DayType.valueOf(it) }
}