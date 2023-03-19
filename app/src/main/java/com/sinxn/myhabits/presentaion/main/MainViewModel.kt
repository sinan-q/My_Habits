package com.sinxn.myhabits.presentaion.main

import androidx.lifecycle.ViewModel
import com.sinxn.myhabits.domain.use_case.settings.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    ) : ViewModel() {
}