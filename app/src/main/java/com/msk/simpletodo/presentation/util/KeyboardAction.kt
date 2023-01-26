package com.msk.simpletodo.presentation.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardAction(val activity: Activity, val v: View? = null) {
    private val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun hideKeyboard() {
        imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    fun showKeyboard() {
        imm.showSoftInput(v, 0)
    }
}