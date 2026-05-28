package com.azyabon.englishwords

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.wordsDataStore: DataStore<Preferences> by preferencesDataStore(name = "learned_words")