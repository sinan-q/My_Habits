package com.sinxn.myhabits.presentaion.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinxn.myhabits.domain.model.TaskAndProgress
import com.sinxn.myhabits.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val taskRepository: TaskRepository,

    ) : ViewModel() {

    var dashboardUiState by mutableStateOf(UiState())
        private set
    private var getTasksJob: Job? = null


    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.InitAll -> {
                getTasksJob?.cancel()
                getTasksJob = taskRepository.getAllTasks(dashboardUiState.date)
                    .onEach { tasks ->

                        dashboardUiState = dashboardUiState.copy(
                            tasks = tasks
                        )
                    }
                    .launchIn(viewModelScope)
            }
        }
    }


    data class UiState(
        val tasks: List<TaskAndProgress> = emptyList(),
        val error: String? = null,
        val today: Long = LocalDate.now().toEpochDay(),
        val date: Long = LocalDate.now().minusDays(7).toEpochDay(),

        var dateRow: MutableList<DateRowClass> = mutableListOf(),

        )

}