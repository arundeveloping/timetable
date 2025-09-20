package com.example.timetable.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID

@Database(entities = [ScheduleItem::class, Completion::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun completionDao(): CompletionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "timetable.db")
                .addCallback(object : Callback() {
                    override fun onCreate(dbx: SupportSQLiteDatabase) {
                        super.onCreate(dbx)
                        CoroutineScope(Dispatchers.IO).launch { prepopulate(context) }
                    }
                })
                .build()
            INSTANCE = db
            db
        }

        private suspend fun prepopulate(context: Context) {
            val dao = get(context).scheduleDao()
            val weekday = listOf(
                si(DayType.WEEKDAY, 3,30, 5,30, "Deep Learning Block 1", "Rotate Azure DevOps / GenAI / Data Structures"),
                si(DayType.WEEKDAY, 5,30, 6,0,  "Pre‑workout & prep", "Coffee + banana / light snack"),
                si(DayType.WEEKDAY, 6,0,  8,0,  "Gym", "Muscle + abs"),
                si(DayType.WEEKDAY, 8,0,  9,0,  "Breakfast & get ready"),
                si(DayType.WEEKDAY, 9,0,  11,0, "Metro commute (study light/DSA)"),
                si(DayType.WEEKDAY, 11,0, 17,0, "Office (6 hrs minimum)", "Deep work focus"),
                si(DayType.WEEKDAY, 17,0, 19,0, "Metro commute back", "Unwind / podcasts"),
                si(DayType.WEEKDAY, 19,0, 20,0, "Dinner & relax"),
                si(DayType.WEEKDAY, 20,0, 21,30, "Focused Learning Block 2", "DS + revision"),
                si(DayType.WEEKDAY, 21,30, 3,30, "Sleep", "6 hours")
            )
            val weekend = listOf(
                si(DayType.WEEKEND, 3,30, 6,0,  "Long Study Block", "Azure DevOps / DS deep dive"),
                si(DayType.WEEKEND, 6,0,  8,0,  "Gym", "Add cardio/abs"),
                si(DayType.WEEKEND, 8,0,  9,0,  "Breakfast & refresh"),
                si(DayType.WEEKEND, 9,0,  12,0, "Study Block 2", "GenAI + projects"),
                si(DayType.WEEKEND, 12,0, 13,0, "Lunch / break"),
                si(DayType.WEEKEND, 13,0, 15,0, "Study Block 3", "DS + mock tests"),
                si(DayType.WEEKEND, 15,0, 18,0, "Leisure / family / friends"),
                si(DayType.WEEKEND, 18,0, 20,0, "Dinner / relax"),
                si(DayType.WEEKEND, 20,0, 21,30, "Light Study / Revision"),
                si(DayType.WEEKEND, 21,30, 3,30, "Sleep", "6 hours")
            )
            dao.insertAll(weekday + weekend)
        }

        private fun si(type: DayType, sh: Int, sm: Int, eh: Int, em: Int, title: String, notes: String = ""): ScheduleItem =
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                dayType = type,
                start = LocalTime.of(sh, sm),
                end = LocalTime.of(eh, em),
                title = title,
                notes = notes
            )
    }
}