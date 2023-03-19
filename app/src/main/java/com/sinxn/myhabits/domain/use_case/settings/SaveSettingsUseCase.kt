package com.sinxn.myhabits.domain.use_case.settings

import androidx.datastore.preferences.core.Preferences
import com.sinxn.myhabits.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
  private val settingsRepository: SettingsRepository
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) = settingsRepository.saveSettings(key, value)
}