package com.sinxn.myhabits.presentaion.settings

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sinxn.myhabits.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,

    ) : ViewModel() {

    fun <T> getSettings(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return settingsRepository.getSettings(key, defaultValue)
    }

    fun <T> saveSettings(key: Preferences.Key<T>, value: T) {
        viewModelScope.launch {
            settingsRepository.saveSettings(key, value)
        }
    }
}