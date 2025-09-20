package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timetable.ui.TimetableViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(vm: TimetableViewModel, padding: PaddingValues) {
    val today = LocalDate.now()
    val dates = remember { (0..13).map { today.minusDays(it.toLong()) } }

    Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Last 14 days", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(dates) { d -> DayRow(d) { vm.setDate(d) } }
        }
    }
}

@Composable
private fun DayRow(date: LocalDate, onOpen: () -> Unit) {
    val fmt = DateTimeFormatter.ofPattern("EEE, dd MMM")
    Card(onClick = onOpen, shape = MaterialTheme.shapes.extraLarge) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(date.format(fmt), style = MaterialTheme.typography.bodyLarge)
            Text("Tap to view", style = MaterialTheme.typography.bodyMedium)
        }
    }
}