package com.msk.simpletodo.presentation.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class KeyboardAction(val activity: Activity) {
    fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}