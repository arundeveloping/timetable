package com.example.timetable.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule_items WHERE dayType = :type ORDER BY start ASC")
    fun itemsFor(type: DayType): Flow<List<ScheduleItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ScheduleItem>)
}

@Dao
interface CompletionDao {
    @Query("SELECT * FROM completions WHERE date = :date")
    fun completionsForDate(date: LocalDate): Flow<List<Completion>>

    @Query("SELECT * FROM completions WHERE date BETWEEN :start AND :end")
    fun completionsBetween(start: LocalDate, end: LocalDate): Flow<List<Completion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(c: Completion)

    @Query("DELETE FROM completions WHERE date = :date AND scheduleId = :scheduleId")
    suspend fun deleteFor(date: LocalDate, scheduleId: String)
}