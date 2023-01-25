package com.msk.simpletodo.presentation.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun <T> Task<T>.toSuspendable(): T = suspendCoroutine { cont ->
    this.addOnCompleteListener { task ->
        when {
            isSuccessful -> cont.resume(task.result)
            isCanceled -> cont.resumeWithException(CancellationException())
            else -> cont.resumeWithException(task.exception ?: Exception("Unknown"))
        }
    }
}