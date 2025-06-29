package com.example.dictionaryapp.core.quiz.storage

import android.database.Cursor
import android.util.Log
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.core.model.WordEngUzb
import com.example.dictionaryapp.core.quiz.ticket.Ticket

object TestsStorage {


    private var tickets = ArrayList<Ticket>()


    fun fillData() {
        val ticketList = ArrayList<Ticket>()
        val cursor = DictionaryDb.getInstance()!!.getSaved()
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {

            val data: WordEngUzb = getModel(cursor)
            val question =
                "Which option is translation of ${data.word}?"

            val answer =
                data.translation.toString()

            val options = getOptions(
                DictionaryDb.getInstance()!!.getQuizOptions(answer, data.type), answer
            )
            ticketList.add(
                Ticket(
                    question,
                    answer,
                    options
                )
            )
            Log.d(
                "getdata", ticketList.toList().toString()
            )
            cursor.moveToNext()
        }


        tickets = ticketList

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
                    data.translation.toString()

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

    fun getData(): ArrayList<Ticket> = tickets


}