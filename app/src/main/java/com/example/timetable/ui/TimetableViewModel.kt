package com.example.timetable.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.DayType
import com.example.timetable.data.Repository
import com.example.timetable.data.ScheduleItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

class TimetableViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository.get(app)

    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<LocalDate> = _date

    private val _dayType = MutableStateFlow(dayTypeFor(_date.value))
    val dayType: StateFlow<DayType> = _dayType

    val items: StateFlow<List<Pair<ScheduleItem, Boolean>>> = _dayType
        .flatMapLatest { dt -> repo.joined(dt, _date.value) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setDate(d: LocalDate) {
        _date.value = d
        _dayType.value = dayTypeFor(d)
    }

    private fun dayTypeFor(d: LocalDate) =
        if (d.dayOfWeek == DayOfWeek.SATURDAY || d.dayOfWeek == DayOfWeek.SUNDAY) DayType.WEEKEND else DayType.WEEKDAY

    fun toggle(scheduleId: String, done: Boolean) {
        viewModelScope.launch { repo.setDone(_date.value, scheduleId, done) }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TimetableViewModel(app) as T
        }
    }
}