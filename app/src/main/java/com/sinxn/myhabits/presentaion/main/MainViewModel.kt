package com.sinxn.myhabits.presentaion.main
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sinxn.myhabits.domain.use_case.settings.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject


data class DateRowClass(val Date: String, val dateString: String)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    ) : ViewModel() {

    var dateRow:MutableList<DateRowClass> = mutableListOf()
    var currentDate by mutableStateOf(LocalDate.now().dayOfMonth)

    init {
        for (i in -4..4) {
            val date = LocalDate.now().plusDays(i.toLong())
            dateRow.add(DateRowClass(date.dayOfMonth.toString(),date.dayOfWeek.name.slice(0..2)))
        }
    }
}