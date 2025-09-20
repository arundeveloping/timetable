package com.example.timetable.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.timetable.ui.screens.HistoryScreen
import com.example.timetable.ui.screens.TodayScreen

@Composable
fun TimetableApp(vm: TimetableViewModel) {
    var tab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Your Timetable Tracker") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tab == 0, onClick = { tab = 0 }, label = { Text("Today") }, icon = { Icon(Icons.Filled.CheckCircle, null) }
                )
                NavigationBarItem(
                    selected = tab == 1, onClick = { tab = 1 }, label = { Text("History") }, icon = { Icon(Icons.Filled.History, null) }
                )
            }
        }
    ) { padding ->
        when (tab) {
            0 -> TodayScreen(vm, padding)
            else -> HistoryScreen(vm, padding)
        }
    }
}