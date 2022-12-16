package com.msk.simpletodo.presentation.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

// extension function for use activity method (hilt)
fun Context.findActivity(): Context {
    while (this is ContextWrapper && this !is Activity) {
        return baseContext
    }
    return this
}