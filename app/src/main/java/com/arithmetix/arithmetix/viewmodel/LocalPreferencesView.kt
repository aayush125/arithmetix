package com.arithmetix.arithmetix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arithmetix.arithmetix.data.PreferenceDataStoreHelper
import com.arithmetix.arithmetix.data.PreferencesKeys
import com.arithmetix.arithmetix.model.AppThemeNames
import com.arithmetix.arithmetix.model.KeypadStyles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocalPreferencesViewModel(
    private val preferenceHelper: PreferenceDataStoreHelper
) : ViewModel() {
    private val _themePreference = MutableStateFlow(AppThemeNames.LIGHT.name)
    val themePreference = _themePreference.asStateFlow()

    private val _keypadPreference = MutableStateFlow(KeypadStyles.CALCULATOR.name)
    val keypadPreference = _keypadPreference.asStateFlow()

    init {
        loadThemePreference()
        loadKeypadPreference()
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            preferenceHelper.getPreference(
                PreferencesKeys.PREF_THEME,
                AppThemeNames.LIGHT.name
            ).collect { theme -> _themePreference.value = theme }
        }
    }

    private fun loadKeypadPreference() {
        viewModelScope.launch {
            preferenceHelper.getPreference(
                PreferencesKeys.PREF_KEYPAD,
                KeypadStyles.CALCULATOR.name
            ).collect { keypad -> _keypadPreference.value = keypad }
        }
    }

    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            preferenceHelper.putPreference(PreferencesKeys.PREF_THEME, newTheme)
        }
    }

    fun updateKeypad(newKeypad: String) {
        viewModelScope.launch {
            preferenceHelper.putPreference(PreferencesKeys.PREF_KEYPAD, newKeypad)
        }
    }
}

class LocalPreferencesViewModelFactory(
    private val preferenceHelper: PreferenceDataStoreHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalPreferencesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocalPreferencesViewModel(preferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}