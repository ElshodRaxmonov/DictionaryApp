package com.example.dictionaryapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.adapter.WordAdapter
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.databinding.FragmentHomeBinding
import java.sql.Array


class FragmentHome : Fragment() {
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var isChanged = false
        var isChangedEdt = false
        binding.listOfWord.adapter = setAdapter(isChanged, false)
        var language = "eng"
        binding.change.setOnClickListener {
            if (!isChanged) {
                isChanged = true
                binding.listOfWord.adapter = setAdapter(isChanged, isChanged)
                binding.textState.text = "Uzb-Eng"
            } else {
                isChanged = false
                binding.listOfWord.adapter = setAdapter(isChanged, isChanged)
                binding.textState.text = "Eng-Uzb"
            }
            language = if (!isChanged) {
                "eng"
            } else {
                "uzb"
            }
        }
        binding.search.setOnClickListener {
            if (isChangedEdt) {
                binding.addWordBtn.show()

                binding.search.setImageResource(R.drawable.search)
                binding.textState.visibility = View.VISIBLE
                binding.change.visibility = View.VISIBLE
                binding.edtSearch.visibility = View.GONE
                binding.edtSearch.text.clear()
                binding.edtSearch.isEnabled = false
                isChangedEdt = false
            } else {

                binding.addWordBtn.hide()
                binding.search.setImageResource(R.drawable.back)
                binding.textState.visibility = View.GONE
                binding.change.visibility = View.GONE
                binding.edtSearch.visibility = View.VISIBLE
                binding.edtSearch.isEnabled = true
                isChangedEdt = true
            }
        }
        binding.edtSearch.addTextChangedListener {

            adapter =
                WordAdapter(requireContext(), DictionaryDb.getInstance()!!.search(it, language))
            adapter.LANGUAGE_CHANGED = isChanged
            binding.listOfWord.adapter = adapter
        }
        binding.addWordBtn.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.add_word_dialog)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawableResource(R.drawable.shape_of_dialog)
            val textEnglish = dialog.findViewById<EditText>(R.id.input_english)
            val textTranscript = dialog.findViewById<EditText>(R.id.input_transcript)
            val spinner = dialog.findViewById<AutoCompleteTextView>(R.id.options)
            val types = arrayOf("noun", "adv", "adj", "verb")
            val array = ArrayAdapter<String>(requireContext(), R.layout.option_item, types)
            val textUzbek = dialog.findViewById<EditText>(R.id.input_uzbek)
            val btnAdd = dialog.findViewById<Button>(R.id.add_word_to_db)
            val btnCancel = dialog.findViewById<Button>(R.id.cancel_to_create)
            var type: String = ""
            spinner.setAdapter(array)
            spinner.setOnItemClickListener { parent, view, position, id ->
                type = parent.getItemAtPosition(position).toString()

            }
            btnAdd.setOnClickListener {
                val english = textEnglish.text.toString()
                val transcript = textTranscript.text.toString()

                val uzbek = textUzbek.text.toString()
                if (english.isEmpty() && uzbek.isNotEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "You've not created english word",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (uzbek.isEmpty() && english.isNotEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "You've not created translation",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (english.isEmpty() && uzbek.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Create any word",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    DictionaryDb.getInstance()!!.setWord(english, transcript, uzbek, type)
                    dialog.hide()
                }
            }
            btnCancel.setOnClickListener {
                textEnglish.text.clear()
                textTranscript.text.clear()
                textUzbek.text.clear()
                dialog.hide()
            }

            dialog.show()
        }
        return binding.root
    }


    private fun setAdapter(isChanged: Boolean, lng_changed: Boolean): WordAdapter {

        if (!isChanged) {
            val cursor = DictionaryDb.getInstance()!!.getEngUzb()
            adapter = WordAdapter(requireContext(), cursor)
            adapter.LANGUAGE_CHANGED = lng_changed

        } else {
            val cursor = DictionaryDb.getInstance()!!.getUzbEng()
            adapter = WordAdapter(requireContext(), cursor)
            adapter.LANGUAGE_CHANGED = lng_changed
        }
        return adapter
    }


    companion object {
        fun newInstance() = FragmentHome().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

data class SetWordBase(var english: String, var transcript: String, var uzbek: String)
