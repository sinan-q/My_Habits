package com.sinxn.myhabits.domain.use_case.alarm

import com.sinxn.myhabits.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAllAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke() = alarmRepository.getAlarms()
}