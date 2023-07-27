package com.example.dictionaryapp.core.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.text.Editable
import android.util.Log
import com.example.dictionaryapp.core.dbhelper.DbHelper
import com.example.dictionaryapp.core.model.WordData
import com.example.dictionaryapp.core.model.WordEngUzb

class DictionaryDb private constructor(context: Context) : DbHelper(context, "dic.db") {

    companion object {
        private var wordDb: DictionaryDb? = null
        fun init(context: Context) {
            wordDb = DictionaryDb(context)
        }

        fun getInstance(): DictionaryDb? = wordDb
    }

    fun getEngUzb(): Cursor {
        val query =
            "SELECT _id,english,type,transcript,uzbek,isFav,isHistory FROM dictionary ORDER BY english COLLATE NOCASE ASC"
        return mDataBase.rawQuery(query, arrayOf())
    }

    fun getUzbEng(): Cursor {
        val query =
            "SELECT _id,uzbek,english,isFav,isHistory FROM dictionary ORDER BY uzbek COLLATE NOCASE ASC"
        return mDataBase.rawQuery(query, arrayOf())
    }

    fun getSaved(): Cursor {
        val query =
            "SELECT _id,english,type, transcript,uzbek,isFav,isHistory FROM dictionary WHERE isFav=1"
        return mDataBase.rawQuery(query, arrayOf())
    }

    fun getQuizOptions(start: String, type: String): Cursor {
        val firstLetter = start[0]
        val letter = "${firstLetter.uppercase()} ${firstLetter.lowercase()}"
        val query =
            "SELECT * FROM dictionary WHERE  type='$type' AND english!=\"$start\" AND uzbek != \"$letter\" AND english LIKE '${start[0]}%' LIMIT 3"
        return mDataBase.rawQuery(query, arrayOf())
    }

    fun setSaved(it: WordEngUzb) {
        val content = ContentValues()
        content.put("isFav", 1)
        mDataBase.update("dictionary", content, "_id=?", arrayOf(it.id.toString()))
    }

    fun setUnSaved(it: WordEngUzb) {
        val content = ContentValues()
        content.put("isFav", 0)
        mDataBase.update("dictionary", content, "_id=?", arrayOf(it.id.toString()))
    }

    fun setUnSavedUzb(it: WordData) {
        val content = ContentValues()
        content.put("isFav", 0)
        mDataBase.update("dictionary", content, "_id=?", arrayOf(it.id.toString()))
    }

    fun setSavedUzbEng(it: WordData) {
        val content = ContentValues()
        content.put("isFav", 1)
        mDataBase.update("dictionary", content, "_id=?", arrayOf(it.id.toString()))
    }

    fun setWord(english: String, transcript: String, uzbek: String,type:String) {
        val content = ContentValues()
        content.put("english", english)
        content.put("transcript", transcript)
        content.put("uzbek", uzbek)
        content.put("isHistory",0)
        content.put("isFav", 0)
        content.put("type", type)
        mDataBase.insert("dictionary", null, content)
        Log.d("TTTT", "english: $english transcript: $transcript uzbek: $uzbek  type:$type")
    }

    fun checkSaved(it: WordData): Boolean {
        val query = "SELECT * FROM dictionary WHERE _id=? AND isFav=1"
        return mDataBase.rawQuery(query, arrayOf(it.id.toString())).count == 1
    }

    fun checkSavedEngUzb(it: WordEngUzb): Boolean {
        val query = "SELECT * FROM dictionary WHERE _id=? AND isFav=1"
        return mDataBase.rawQuery(query, arrayOf(it.id.toString())).count == 1
    }

    fun search(text: Editable?, word: String): Cursor {
        val query =
            "SELECT _id,uzbek,english,isFav,isHistory FROM dictionary WHERE uzbek LIKE '$text%' "
        val queryTwo =
            "SELECT _id,english,type,transcript,uzbek,isFav,isHistory FROM dictionary WHERE english LIKE '$text%' "
        return mDataBase.rawQuery(
            if (word == "eng") {
                queryTwo
            } else {
                query
            }, arrayOf()
        )
    }
}
