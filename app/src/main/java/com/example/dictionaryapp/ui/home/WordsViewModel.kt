package com.example.dictionaryapp.ui.home

import android.content.Context
import android.database.Cursor
import android.service.autofill.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.adapter.WordsAdapter
import com.example.dictionaryapp.core.db.DictionaryDb

class WordsViewModel : ViewModel() {
    private var _lngChanged = MutableLiveData<Boolean>()
    val lngChanged: LiveData<Boolean> = _lngChanged
    val inputText = MutableLiveData<String>()
    private val cursorForEng = DictionaryDb.getInstance()!!.getEngUzb()
    private val cursorForUzb = DictionaryDb.getInstance()!!.getUzbEng()

    private val _filteredCursor = MediatorLiveData<Cursor>()
    val filteredCursor: LiveData<Cursor> get() = _filteredCursor

    private val dictionaryDb = DictionaryDb.getInstance()

    init {
        _lngChanged.value = false
        _filteredCursor.addSource(inputText) { query ->
            _filteredCursor.value = dictionaryDb?.search(query ?: "", _lngChanged.value)
        }
    }

    fun replaceLanguage() {
        _lngChanged.value = _lngChanged.value != true
    }


    fun setAdapter(context: Context): WordsAdapter {
        return if (_lngChanged.value == true) {
            WordsAdapter(context, cursorForUzb, _lngChanged.value)
        } else if (_lngChanged.value == false) {
            WordsAdapter(context, cursorForEng, _lngChanged.value)
        } else {
            WordsAdapter(context, filteredCursor.value, _lngChanged.value)
        }

    }


}