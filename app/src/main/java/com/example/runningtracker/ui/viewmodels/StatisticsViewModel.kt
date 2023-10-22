package com.example.runningtracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.runningtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor (private val repository: MainRepository): ViewModel() {

    val totalTime = repository.getTotalTime()
    val totalCalories = repository.getTotalCalories()
    val totalDistance = repository.getTotalDistance()
    val totalAvgSpeed = repository.getAvgSpeed()
    val runningSortedByDate = repository.getAllRunningSortedByDate()

}