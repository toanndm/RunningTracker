package com.example.runningtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningtracker.db.Running
import com.example.runningtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: MainRepository): ViewModel() {

    fun runningSortedByDate() = repository.getAllRunningSortedByDate()
    fun insertRunning(running: Running) = viewModelScope.launch {
        repository.insertRunning(running)
    }
}