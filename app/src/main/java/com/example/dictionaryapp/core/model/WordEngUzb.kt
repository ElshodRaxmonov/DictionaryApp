package com.example.dictionaryapp.core.model

data class WordEngUzb(
    val id: Long,
    val word: String,
    val type: String,
    val transcript: String,
    val translete: String?,
    var isSaved: Int,
    var isHistory:Int
)