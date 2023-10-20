package com.example.runningtracker.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningtracker.db.Running
import com.example.runningtracker.other.SortType
import com.example.runningtracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: MainRepository): ViewModel() {

    private val runningSortedByDate = repository.getAllRunningSortedByDate()
    private val runningSortedByCalories = repository.getAllRunningSortedByCalories()
    private val runningSortedByTime = repository.getAllRunningSortedByTime()
    private val runningSortedByAvgSpeed = repository.getAllRunningSortedByAvgSpeed()
    private val runningSortedByDistance = repository.getAllRunningSortedByDistance()

    var sortType = SortType.DATE

    val runnings = MediatorLiveData<List<Running>>()

    init {
        runnings.addSource(runningSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let {
                    runnings.value = it
                }
            }
        }
        runnings.addSource(runningSortedByDistance) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let {
                    runnings.value = it
                }
            }
        }
        runnings.addSource(runningSortedByCalories) { result ->
            if (sortType == SortType.CALORIES_BURNED) {
                result?.let {
                    runnings.value = it
                }
            }
        }
        runnings.addSource(runningSortedByTime) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let {
                    runnings.value = it
                }
            }
        }
        runnings.addSource(runningSortedByAvgSpeed) { result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let {
                    runnings.value = it
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runningSortedByDate.value?.let { runnings.value = it }
        SortType.DISTANCE -> runningSortedByDistance.value?.let { runnings.value = it }
        SortType.CALORIES_BURNED -> runningSortedByCalories.value?.let { runnings.value = it }
        SortType.RUNNING_TIME -> runningSortedByTime.value?.let { runnings.value = it }
        SortType.AVG_SPEED -> runningSortedByAvgSpeed.value?.let { runnings.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRunning(running: Running) = viewModelScope.launch {
        repository.insertRunning(running)
    }
}