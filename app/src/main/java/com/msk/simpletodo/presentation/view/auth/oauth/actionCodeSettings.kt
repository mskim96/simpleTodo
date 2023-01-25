package com.msk.simpletodo.presentation.view.auth.oauth

import com.google.firebase.auth.ktx.actionCodeSettings
import com.msk.simpletodo.BuildConfig

val actionCodeSetting = actionCodeSettings {
    url = "https://simpletodo-56808.firebaseapp.com"
    handleCodeInApp = false
    setAndroidPackageName(BuildConfig.APPLICATION_ID, true, null)
}