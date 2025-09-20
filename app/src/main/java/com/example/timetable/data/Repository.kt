package com.example.timetable.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class Repository private constructor(context: Context) {
    private val db = AppDatabase.get(context)
    private val scheduleDao = db.scheduleDao()
    private val completionDao = db.completionDao()

    fun schedule(dayType: DayType) = scheduleDao.itemsFor(dayType)

    fun completionsFor(date: LocalDate) = completionDao.completionsForDate(date)

    suspend fun setDone(date: LocalDate, scheduleId: String, done: Boolean) {
        if (done) completionDao.upsert(Completion(date = date, scheduleId = scheduleId, done = true))
        else completionDao.deleteFor(date, scheduleId)
    }

    fun joined(dayType: DayType, date: LocalDate): Flow<List<Pair<ScheduleItem, Boolean>>> =
        schedule(dayType).combine(completionsFor(date)) { items, comps ->
            val set = comps.filter { it.done }.map { it.scheduleId }.toSet()
            items.map { it to set.contains(it.id) }
        }

    companion object {
        @Volatile private var INSTANCE: Repository? = null
        fun get(context: Context): Repository = INSTANCE ?: synchronized(this) {
            Repository(context.applicationContext).also { INSTANCE = it }
        }
    }
}