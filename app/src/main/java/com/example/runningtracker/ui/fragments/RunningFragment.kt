package com.example.runningtracker.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.runningtracker.R
import com.example.runningtracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunningFragment : Fragment(R.layout.fragment_running) {
    private val mainViewModel: MainViewModel by viewModels()
}