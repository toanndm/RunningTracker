package com.example.runningtracker.ui.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.runningtracker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog : DialogFragment() {

    private var onClickYesListener : (() -> Unit?)? = null

    fun setOnClickYes (listener : () -> Unit) {
        onClickYesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure to cancel this run and delete all its data?")
            .setPositiveButton("Yes") { _, _ ->
                onClickYesListener?.let {
                    it()
                }
            }
            .setNegativeButton("No") { dialog: DialogInterface, _ ->
                dialog.cancel()
            }
            .create()
    }
}