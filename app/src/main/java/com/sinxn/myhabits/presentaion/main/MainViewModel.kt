package com.sinxn.myhabits.presentaion.main
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhss.app.mybrain.domain.model.Task
import com.sinxn.myhabits.R
import com.sinxn.myhabits.app.getString
import com.sinxn.myhabits.domain.model.Alarm
import com.sinxn.myhabits.domain.use_case.alarm.AddAlarmUseCase
import com.sinxn.myhabits.domain.use_case.alarm.DeleteAlarmUseCase
import com.sinxn.myhabits.domain.use_case.task.*
import com.sinxn.myhabits.util.settings.Order
import com.sinxn.myhabits.util.settings.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


data class DateRowClass(val Date: String, val dateString: String)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val addTask: AddTaskUseCase,
    private val getAllTasks: GetAllTasksUseCase,
    private val getTaskUseCase: GetTaskByIdUseCase,
    private val updateTask: UpdateTaskUseCase,
    private val completeTask: UpdateTaskCompletedUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val searchTasksUseCase: SearchTasksUseCase,
    private val addAlarm: AddAlarmUseCase,
    private val deleteAlarm: DeleteAlarmUseCase,
    ) : ViewModel() {

    var tasksUiState by mutableStateOf(UiState())
        private set
    var taskDetailsUiState by mutableStateOf(TaskUiState())
        private set

    private var getTasksJob: Job? = null
    private var searchTasksJob: Job? = null


    var dateRow:MutableList<DateRowClass> = mutableListOf()
    var currentDate by mutableStateOf(LocalDate.now().dayOfMonth)

    init {
        for (i in -4..4) {
            val date = LocalDate.now().plusDays(i.toLong())
            dateRow.add(DateRowClass(date.dayOfMonth.toString(),date.dayOfWeek.name.slice(0..2)))
        }
        viewModelScope.launch {

            getTasks()

        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> {
                if (event.task.title.isNotBlank()) {
                    viewModelScope.launch {
                        val taskId = addTask(event.task)
                        if (event.task.remainder) addAlarm(Alarm(taskId.toInt(),))
                    }

                }else
                    tasksUiState = tasksUiState.copy(error = getString(R.string.error_empty_title))
            }

            is TaskEvent.CompleteTask -> viewModelScope.launch {
                completeTask(event.task.id, event.complete)
                if (event.complete)
                    deleteAlarm(event.task.id)
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
                    searchTasks(event.query)
                }
            }
            is TaskEvent.UpdateTask -> viewModelScope.launch {
                if (event.task.title.isBlank())
                    taskDetailsUiState = taskDetailsUiState.copy(error = getString(R.string.error_empty_title))
                else {
                    updateTask(event.task.copy(updatedDate = System.currentTimeMillis()))
//                    if (event.task.dueDate != taskDetailsUiState.task.dueDate){
//                        if (event.task.dueDate != 0L)
//                            addAlarm(
//                                Alarm(
//                                    event.task.id,
//                                    event.task.dueDate
//                                )
//                            )
//                        else
//                            deleteAlarm(event.task.id)
//                    }
                    taskDetailsUiState = taskDetailsUiState.copy(navigateUp = true)
                }
            }
            is TaskEvent.DeleteTask -> viewModelScope.launch {
                deleteTask(event.task)
                if (event.task.remainder) deleteAlarm(event.task.id)
                taskDetailsUiState = taskDetailsUiState.copy(navigateUp = true)
            }
            is TaskEvent.GetTask -> viewModelScope.launch {
                taskDetailsUiState = taskDetailsUiState.copy(
                    task = getTaskUseCase(event.taskId)
                )
            }
            else -> {}
        }
    }

    data class UiState(
        val goodTasks: List<Task> = emptyList(),
        val badTasks: List<Task> = emptyList(),

        val taskOrder: Order = Order.DateModified(OrderType.ASC()),
        val showCompletedTasks: Boolean = false,
        val error: String? = null,
        val searchTasks: List<Task> = emptyList()
    )

    data class TaskUiState(
        val task: Task = Task("", "emoji"),
        val navigateUp: Boolean = false,
        val error: String? = null
    )

    private fun getTasks(order: Order = Order.DateCreated(), showCompleted: Boolean = true) {
        getTasksJob?.cancel()
        getTasksJob = getAllTasks(order)
            .map { list ->
                if (showCompleted)
                    list
                else
                    list.filter { !it.isCompleted }
            }.onEach { tasks ->
                tasksUiState = tasksUiState.copy(
                    goodTasks = tasks.filter { it.category },
                    badTasks = tasks.filter { !it.category },
                    taskOrder = order,
                    showCompletedTasks = showCompleted
                )
            }.launchIn(viewModelScope)
    }
    private fun searchTasks(query: String){
        searchTasksJob?.cancel()
        searchTasksJob = searchTasksUseCase(query).onEach { tasks ->
            tasksUiState = tasksUiState.copy(
                searchTasks = tasks
            )
        }.launchIn(viewModelScope)
    }
}