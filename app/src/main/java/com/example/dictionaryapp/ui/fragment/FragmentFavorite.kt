package com.example.dictionaryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.core.adapter.WordAdapter
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.databinding.FragmentFavoriteBinding

class FragmentFavorite : Fragment() {

            private val binding by lazy {
                FragmentFavoriteBinding.inflate(layoutInflater)
            }

    private lateinit var adapter: WordAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cursor=DictionaryDb.getInstance()!!.getSaved()
        adapter=WordAdapter(requireContext(),cursor)
        binding.listOfWord.adapter=adapter
    }


    companion object {
        fun newInstance() = FragmentFavorite().apply {
            arguments = Bundle().apply {

            }

        }
    }
}