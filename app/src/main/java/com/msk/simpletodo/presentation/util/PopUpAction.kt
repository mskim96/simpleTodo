package com.msk.simpletodo.presentation.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class PopUpAction {
    fun emptySnackBar(v: View, message: String?) {
        Snackbar.make(v, message ?: "Empty", Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }
}