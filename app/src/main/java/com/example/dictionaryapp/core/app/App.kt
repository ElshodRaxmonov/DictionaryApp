package com.example.dictionaryapp.core.app

import android.app.Application
import com.example.dictionaryapp.core.db.DictionaryDb

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DictionaryDb.init(this)
    }
}