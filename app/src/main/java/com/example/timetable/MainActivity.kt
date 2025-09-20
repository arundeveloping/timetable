package com.example.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetable.ui.TimetableApp
import com.example.timetable.ui.TimetableViewModel
import com.example.timetable.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val vm: TimetableViewModel = viewModel(factory = TimetableViewModel.Factory(application))
                TimetableApp(vm)
            }
        }
    }
}