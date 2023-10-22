package com.example.runningtracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.material.Snackbar
import androidx.fragment.app.Fragment
import com.example.runningtracker.R
import com.example.runningtracker.databinding.FragmentSettingBinding
import com.example.runningtracker.other.Constant.KEY_NAME
import com.example.runningtracker.other.Constant.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private lateinit var binding: FragmentSettingBinding
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvToolbar: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        tvToolbar = requireActivity().findViewById(R.id.tvToolbarTitle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPreferences()
        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPreferences()
            if (success) {
                com.google.android.material.snackbar.Snackbar.make(view, "Saved changes!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun applyChangesToSharedPreferences(): Boolean {
        val nameText = binding.etName.text.toString()
        val weightText = binding.etWeight.text.toString()
        if (nameText.isEmpty() || weightText.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()
        val text = "Let's go $nameText!"
        tvToolbar.text = text
        return true
    }

    private fun loadFieldsFromSharedPreferences() {
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 70f)
        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())
    }
}