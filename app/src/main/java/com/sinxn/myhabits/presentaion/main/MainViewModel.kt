package com.sinxn.myhabits.presentaion.main
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinxn.myhabits.R
import com.sinxn.myhabits.app.getString
import com.sinxn.myhabits.domain.model.Progress
import com.sinxn.myhabits.domain.model.Task
import com.sinxn.myhabits.domain.model.TaskWithProgress
import com.sinxn.myhabits.domain.repository.TaskRepository
import com.sinxn.myhabits.util.settings.Order
import com.sinxn.myhabits.util.settings.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


data class DateRowClass(val epoch: Long, val Date: String, val dateString: String)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskRepository: TaskRepository,

    ) : ViewModel() {

    var tasksUiState by mutableStateOf(UiState())
        private set
    var taskDetailsUiState by mutableStateOf(TaskUiState())
        private set

    private var getTasksJob: Job? = null
    private var searchTasksJob: Job? = null


    init {
        viewModelScope.launch {
            for (i in -4..4) {
                val date = LocalDate.now().plusDays(i.toLong())
                tasksUiState.dateRow.add(
                    DateRowClass(
                        date.toEpochDay(),
                        date.dayOfMonth.toString(),
                        date.dayOfWeek.name.slice(0..2)
                    )
                )
            }
            getTasks(LocalDate.now().toEpochDay())

        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> {
                if (event.task.title.isNotBlank()) {
                    viewModelScope.launch {
                        val taskId = taskRepository.insertTask(event.task)
                    }

                }else
                    tasksUiState = tasksUiState.copy(error = getString(R.string.error_empty_title))
            }

            is TaskEvent.CompleteTask -> viewModelScope.launch {
                //   taskRepository.completeTask(event.date ,event.task.habitId, event.complete)

            }
            TaskEvent.ErrorDisplayed -> {
                tasksUiState = tasksUiState.copy(error = null)
                taskDetailsUiState = taskDetailsUiState.copy(error = null)
            }
//            is TaskEvent.UpdateOrder -> viewModelScope.launch {
//                saveSettings(
//                    intPreferencesKey(Constants.TASKS_ORDER_KEY),
//                    event.order.toInt()
//                )
//            }
//            is TaskEvent.ShowCompletedTasks -> viewModelScope.launch {
//                saveSettings(
//                    booleanPreferencesKey(Constants.SHOW_COMPLETED_TASKS_KEY),
//                    event.showCompleted
//                )
//            }
            is TaskEvent.SearchTasks -> {
                viewModelScope.launch {
                    searchTasks(tasksUiState.date,event.query)
                }
            }
            is TaskEvent.OnDateChange -> viewModelScope.launch {
                tasksUiState = tasksUiState.copy(date = event.date)
                getTasks(event.date)
            }
            is TaskEvent.UpdateProgress -> viewModelScope.launch {
                taskRepository.updateTaskProgress(
                    event.progress,
                    today = LocalDate.now().toEpochDay()
                )
            }
            is TaskEvent.UpdateTask -> viewModelScope.launch {
                if (event.task.title.isBlank())
                    taskDetailsUiState = taskDetailsUiState.copy(error = getString(R.string.error_empty_title))
                else {
                    taskRepository.updateTask(event.task.copy(updatedDate = System.currentTimeMillis()))
                }
            }
            is TaskEvent.DeleteTask -> viewModelScope.launch {

                taskRepository.deleteTask(event.task)


            }
            is TaskEvent.SetTask -> viewModelScope.launch {

                taskDetailsUiState = taskDetailsUiState.copy(
                    title = event.task.title,
                    emoji = event.task.emoji,
                    progress = event.task.progress!!

                )
            }
            else -> {}
        }
    }

    data class UiState(
        val goodTasks: List<TaskWithProgress> = emptyList(),
        val badTasks: List<TaskWithProgress> = emptyList(),

        val taskOrder: Order = Order.DateModified(OrderType.ASC()),
        val showCompletedTasks: Boolean = false,
        val error: String? = null,
        val date: Long = LocalDate.now().toEpochDay(),
        var dateRow: MutableList<DateRowClass> = mutableListOf(),


        val searchTasks: List<Task> = emptyList()
    )

    data class TaskUiState(
        val title: String = "",
        val emoji: String = "",
        val progress: Progress = Progress(),
        val error: String? = null
    )

    private fun getTasks(date: Long ,order: Order = Order.DateCreated(), showCompleted: Boolean = true) {
        getTasksJob?.cancel()
        getTasksJob = taskRepository.getAllTasks(date)
            .onEach { tasks ->

                tasksUiState = tasksUiState.copy(
                    goodTasks = tasks.filter { it.category },
                    badTasks = tasks.filter { !it.category },
                    taskOrder = order,
                    showCompletedTasks = showCompleted
                )
            }.launchIn(viewModelScope)
    }
    private fun searchTasks(date:Long,query: String){
        searchTasksJob?.cancel()
        searchTasksJob = taskRepository.searchTasks(date, query).onEach { tasks ->
            tasksUiState = tasksUiState.copy(
                searchTasks = tasks
            )
        }.launchIn(viewModelScope)
    }
}