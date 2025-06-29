package com.example.dictionaryapp.core.adapter

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.core.model.WordData
import com.example.dictionaryapp.core.model.WordEngUzb
import net.cachapa.expandablelayout.ExpandableLayout

class WordsAdapter(context: Context, cursor: Cursor, lngChanged: Boolean) :
    CursorAdapter(context, cursor, false) {


    val lngState = lngChanged
    override fun newView(
        context: Context?,
        cursor: Cursor?,
        parent: ViewGroup?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)

        return view
    }


    override fun bindView(
        view: View?,
        context: Context?,
        cursor: Cursor?
    ) {
        if (view == null || cursor == null) return

        val textView: TextView = view.findViewById(R.id.word)
        val translation = view.findViewById<TextView>(R.id.word_translation)
        val type = view.findViewById<TextView>(R.id.word_type)
        val transcript = view.findViewById<TextView>(R.id.word_transcript)
        val saved = view.findViewById<CheckBox>(R.id.saved)
        val layout = view.findViewById<ExpandableLayout>(R.id.expandable)

        view.animation = AnimationUtils.loadAnimation(context, R.anim.animate)

        if (lngState) {
            val word = getWordDataUzbEng(cursor)
            textView.text = word.word
            translation.text = word.translation
            type.visibility = View.GONE
            transcript.visibility = View.GONE
            saved.isChecked = DictionaryDb.getInstance()!!.checkSaved(word)

            saved.setOnClickListener {
                if (saved.isChecked) {
                    DictionaryDb.getInstance()!!.setSavedUzbEng(word)
                } else {
                    DictionaryDb.getInstance()!!.setUnSavedUzb(word)
                }
            }

        } else {
            val word = getWordDataEngUzb(cursor)
            textView.text = word.word
            translation.text = word.translation
            type.text = word.type
            transcript.text = word.transcript
            type.visibility = View.VISIBLE
            transcript.visibility = View.VISIBLE
            saved.isChecked = DictionaryDb.getInstance()!!.checkSavedEngUzb(word)

            saved.setOnClickListener {
                if (saved.isChecked) {
                    DictionaryDb.getInstance()!!.setSaved(word)
                } else {
                    DictionaryDb.getInstance()!!.setUnSaved(word)
                }
            }
        }

        view.setOnClickListener {
            layout.toggle()
        }


    }


    private fun getWordDataEngUzb(cursor: Cursor?): WordEngUzb {
        return WordEngUzb(
            cursor!!.getLong(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5),
            cursor.getInt(6)
        )
    }

    private fun getWordDataUzbEng(cursor: Cursor?): WordData {
        return WordData(
            cursor!!.getLong(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getInt(3),
            cursor.getInt(4)
        )
    }


}