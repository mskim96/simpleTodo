package com.msk.simpletodo.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val USER_USERNAME = "username"

class PreferDatastore @Inject constructor(private val preferencesDataStore: DataStore<Preferences>) {

    private val dataStoreUsername = stringPreferencesKey(USER_USERNAME)

    suspend fun putUsernameOnDataStore(username: String) {
        preferencesDataStore.edit { setData ->
            setData[dataStoreUsername] = username
        }
    }

    fun getUsernameOnDataStore(): Flow<String?> {
        val result = preferencesDataStore.data.map { data ->
            data[dataStoreUsername]
        }
        return result
    }
}