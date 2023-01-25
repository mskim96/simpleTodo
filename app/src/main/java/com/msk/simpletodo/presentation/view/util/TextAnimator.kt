package com.msk.simpletodo.presentation.view.util

import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

fun hideTextView(view: View) {
    val subTitleHeight = view.height
    val heightAnimator = ValueAnimator.ofInt(subTitleHeight, 0)
    heightAnimator.addUpdateListener {
        view.updateHeight(it.animatedValue as Int)
    }
    heightAnimator.start()
}

fun showTextView(textView: TextView) {
    textView.updateHeight(ConstraintLayout.LayoutParams.WRAP_CONTENT)
    val subtitleHeight = textView.measuredHeight
    textView.measure(0, View.MeasureSpec.UNSPECIFIED)
    textView.height = 0
    val heightAnimator = ValueAnimator.ofInt(0, subtitleHeight)
    heightAnimator.addUpdateListener {
        textView.height = it.animatedValue as Int
    }
    heightAnimator.start()
}

fun View.updateHeight(newHeight: Int) {
    layoutParams = layoutParams.apply {
        height = newHeight
    }
}