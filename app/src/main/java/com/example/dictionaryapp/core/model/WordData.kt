package com.example.dictionaryapp.core.model

data class WordData(
    val id: Long,
    val word: String,
    val translation: String,
    var isFav: Int,
    var isHistory: Int
)
