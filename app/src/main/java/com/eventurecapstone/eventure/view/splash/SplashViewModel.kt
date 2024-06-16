package com.eventurecapstone.eventure.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eventurecapstone.eventure.repository.DataStoreRepository
import kotlinx.coroutines.launch
import java.util.Locale

class SplashViewModel(private val dataStoreRepository: DataStoreRepository): ViewModel() {
    val systemLanguage: LiveData<String?> = dataStoreRepository.language().asLiveData()
    fun setLanguage(lang: Locale){
        viewModelScope.launch {
            dataStoreRepository.setLanguage(lang)
        }
    }

    val systemTheme: LiveData<Boolean?> = dataStoreRepository.nightMode().asLiveData()
    fun setThemeToNight(state: Boolean = true){
        viewModelScope.launch {
            dataStoreRepository.setNightMode(state)
        }
    }

    var languageDone = false
    var themeDone = false
    private val _doneSignal = MutableLiveData<Boolean>()
    val doneSignal: LiveData<Boolean> get() = _doneSignal

    fun markJobAsDone(language: Boolean = false, theme: Boolean = false){
        if (language) languageDone = true
        if (theme) themeDone = true
        _doneSignal.postValue(true)
    }
}