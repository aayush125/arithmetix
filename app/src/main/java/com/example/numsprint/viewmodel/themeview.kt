package com.example.numsprint.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.numsprint.data.PreferenceDataStoreHelper
import com.example.numsprint.data.PreferencesKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferenceHelper: PreferenceDataStoreHelper) : ViewModel() {
    private val _themePreference = MutableStateFlow("default_theme")
    val themePreference = _themePreference.asStateFlow()

    init {
        loadThemePreference()
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            preferenceHelper.getPreference(PreferencesKeys.PREF_THEME, "default_theme")
                .collect { theme -> _themePreference.value = theme }
        }
    }

    fun updateTheme(newTheme: String) {
        viewModelScope.launch {
            preferenceHelper.putPreference(PreferencesKeys.PREF_THEME, newTheme)
        }
    }
}

class ThemeViewModelFactory(
    private val preferenceHelper: PreferenceDataStoreHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(preferenceHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}