package com.example.dictionaryapp.core.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.core.database.getStringOrNull
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.core.model.WordData
import com.example.dictionaryapp.core.model.WordEngUzb
import net.cachapa.expandablelayout.ExpandableLayout

class WordAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, false) {

    var LANGUAGE_CHANGED = false
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return view
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val wordData: Any
        val textView: TextView = view.findViewById(R.id.word)
        val translete = view.findViewById<TextView>(R.id.word_translation)
        val type = view.findViewById<TextView>(R.id.word_type)
        val transcript = view.findViewById<TextView>(R.id.word_transcript)
        val saved = view.findViewById<CheckBox>(R.id.saved)
        view.animation = AnimationUtils.loadAnimation(context, R.anim.animate)

        when (LANGUAGE_CHANGED) {
            true -> {
                wordData = getWordDataUzbEng(cursor)
                textView.text = wordData.word
                translete.text = wordData.translete
                type.visibility = View.GONE
                transcript.visibility = View.GONE
                saved.isChecked = DictionaryDb.getInstance()!!.checkSaved(wordData)
            }

            false -> {
                wordData = getWordDataEngUzb(cursor)
                textView.text = wordData.word
                type.text = wordData.type
                transcript.text = wordData.transcript
                translete.text = wordData.translete
                saved.isChecked = DictionaryDb.getInstance()!!.checkSavedEngUzb(wordData)
            }
        }


        saved.setOnClickListener {
            if (saved.isChecked) {
                Log.d("TTT", "Saqlandim")
                saved.isChecked = true
                DictionaryDb.getInstance()!!.apply {
                    if (LANGUAGE_CHANGED) {
                        setSavedUzbEng(wordData as WordData)
                    } else {
                        setSaved(wordData as WordEngUzb)
                    }
                }
            } else {
                saved.isChecked = false
                DictionaryDb.getInstance()!!.apply {
                    if (LANGUAGE_CHANGED) {
                        setUnSavedUzb(wordData as WordData)
                    } else {
                        setUnSaved(wordData as WordEngUzb)
                    }
                }
            }
        }

        val layout = view.findViewById<ExpandableLayout>(R.id.expandable)
        view.setOnClickListener {
            layout.toggle()
        }


    }

    private fun getWordDataEngUzb(cursor: Cursor): WordEngUzb {
        Log.d("ttt", "manaaa Eng -Uzb")
        Log.d("ttt", "$LANGUAGE_CHANGED")
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

    private fun getWordDataUzbEng(cursor: Cursor): WordData {
        return WordData(
            cursor.getLong(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getInt(3),
            cursor.getInt(4)
        )
    }


}