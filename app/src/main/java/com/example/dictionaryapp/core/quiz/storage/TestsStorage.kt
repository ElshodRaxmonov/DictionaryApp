package com.example.dictionaryapp.core.quiz.storage

import android.database.Cursor
import android.util.Log
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.core.model.WordEngUzb
import com.example.dictionaryapp.core.quiz.ticket.Ticket

object TestsStorage {

    var LANGUAGE_CHANGED = false
    private var oldTickets = ArrayList<Ticket>()


    fun fillData() {
        val tickets = ArrayList<Ticket>()
        val cursor = DictionaryDb.getInstance()!!.getSaved()
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {

            val data: WordEngUzb = getModel(cursor)
            val question = if (LANGUAGE_CHANGED) {
                "${data.translete.toString()} - ushbu so'zning ma'nosi qaysi javobda to'g'ri ko'rsatilgan?"
            } else {
                "Which option is translate of ${data.word.toString()}?"
            }
            val answer = if (LANGUAGE_CHANGED) {
                data.word.toString()
            } else {
                data.translete.toString()
            }
            val options = getOptions(
                DictionaryDb.getInstance()!!.getQuizOptions(answer, data.type), answer
            )
            tickets.add(
                Ticket(
                    question,
                    answer,
                    options
                )
            )
            Log.d(
                "getdata", tickets.toList().toString()
            )
            cursor.moveToNext()
        }
        cursor.close()
        oldTickets = tickets
    }

//    fun clearData() {
//        tickets.clear()
//    }

    private fun getOptions(cursor: Cursor, answer: String): ArrayList<String> {

        cursor.moveToFirst()

        val options = ArrayList<String>()
        options.add(answer)
        while (!cursor.isAfterLast) {
            val data = getModel(cursor)
            options.add(
                if (LANGUAGE_CHANGED) {
                    data.word.toString()
                } else {
                    data.translete.toString()
                }
            )
            cursor.moveToNext()
        }
        cursor.close()
        return options

    }

    private fun getModel(cursor: Cursor): WordEngUzb {
        return WordEngUzb(
            cursor.getLong(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5),
            cursor.getInt(6)
        )
    }

    fun getData(): ArrayList<Ticket> = oldTickets


}