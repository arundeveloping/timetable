package com.example.timetable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timetable.data.ScheduleItem
import com.example.timetable.ui.TimetableViewModel
import java.time.format.DateTimeFormatter

@Composable
fun TodayScreen(vm: TimetableViewModel, padding: PaddingValues) {
    val items by vm.items.collectAsState()
    val progress = if (items.isEmpty()) 0f else items.count { it.second }.toFloat() / items.size

    Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Today", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
        Text("${(progress * 100).toInt()}% complete", style = MaterialTheme.typography.bodyMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
            items(items) { (item, done) -> ScheduleRow(item, done) { vm.toggle(item.id, it) } }
        }
    }
}

@Composable
private fun ScheduleRow(item: ScheduleItem, done: Boolean, onToggle: (Boolean) -> Unit) {
    val tf = DateTimeFormatter.ofPattern("h:mm a")
    Card(shape = MaterialTheme.shapes.extraLarge) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Checkbox(checked = done, onCheckedChange = onToggle)
            Column(Modifier.weight(1f)) {
                Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text("${item.start.format(tf)} — ${item.end.format(tf)}", style = MaterialTheme.typography.bodySmall)
                if (item.notes.isNotBlank()) Text(item.notes, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}