package com.example.dictionaryapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.adapter.WordsAdapter
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.databinding.FragmentWordsBinding

class WordsFragment : Fragment() {

    companion object {
        fun newInstance() = WordsFragment()

    }

    private val vM: WordsViewModel by viewModels()
    lateinit var binding: FragmentWordsBinding
    lateinit var adapter: WordsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_words, container, false)

        binding.vm = vM
        binding.lifecycleOwner = viewLifecycleOwner

        binding.listOfWord.adapter = vM.setAdapter(requireContext())




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vM.filteredCursor.observe(viewLifecycleOwner) { cursor ->
            cursor?.let {
                binding.listOfWord.adapter = WordsAdapter(requireContext(), it, vM.lngChanged.value)

            }
        }
        binding.change.setOnClickListener {

            vM.replaceLanguage()
            vM.inputText.value = null
            if (!vM.lngChanged.value) {
                binding.change.setImageResource(R.drawable.uzb)
                binding.listOfWord.adapter = vM.setAdapter(requireContext())
            } else {
                binding.change.setImageResource(R.drawable.eng)
                binding.listOfWord.adapter = vM.setAdapter(requireContext())

            }
        }
    }



}