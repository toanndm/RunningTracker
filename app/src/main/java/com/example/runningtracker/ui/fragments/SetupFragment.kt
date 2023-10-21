package com.example.runningtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.compose.material.Snackbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.runningtracker.R
import com.example.runningtracker.databinding.FragmentSetupBinding
import com.example.runningtracker.other.Constant.KEY_FIRST_TOGGLE
import com.example.runningtracker.other.Constant.KEY_NAME
import com.example.runningtracker.other.Constant.KEY_WEIGHT
import com.example.runningtracker.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {
    private lateinit var binding: FragmentSetupBinding

    @set:Inject
    var isFirstRun = true

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvToolbar: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetupBinding.inflate(inflater, container, false)
        tvToolbar = requireActivity().findViewById(R.id.tvToolbarTitle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstRun) {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runningFragment,
                savedInstanceState,
                navOption
            )
        }

        binding.tvContinue.setOnClickListener {
            val success = writePersonalDataPreferences()
            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runningFragment)
            } else {
                com.google.android.material.snackbar.Snackbar.make(
                    requireView(),
                    "Please enter your name and weight!",
                    com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun writePersonalDataPreferences() : Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TOGGLE, false)
            .apply()
        val toolbarText = "Let's go $name!"

        tvToolbar.text = toolbarText

        return true
    }
}