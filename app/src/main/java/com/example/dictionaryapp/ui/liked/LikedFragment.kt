package com.example.dictionaryapp.ui.liked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.adapter.WordsAdapter
import com.example.dictionaryapp.core.db.DictionaryDb
import com.example.dictionaryapp.databinding.FragmentFavouriteBinding

class LikedFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val likedViewModel =
            ViewModelProvider(this).get(LikedViewModel::class.java)

        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_favourite,container,false)

        _binding!!.listOfWord.adapter= WordsAdapter(requireContext(), DictionaryDb.getInstance()!!.getSaved(),false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}