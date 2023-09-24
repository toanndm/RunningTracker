package com.example.runningtracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.runningtracker.R
import com.example.runningtracker.ui.viewmodels.MainViewModel

class TrackingFragment : Fragment(R.layout.fragment_tracking) {
    private val mainViewModel: MainViewModel by viewModels()
}