package com.msk.simpletodo.presentation.util

import android.content.Context

fun getDrawableId(context: Context, iconName: String): Int {
    return context.resources.getIdentifier(
        iconName, "drawable", "com.msk.simpletodo"
    )
}