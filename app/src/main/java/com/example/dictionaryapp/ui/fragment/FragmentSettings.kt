package com.example.dictionaryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.databinding.FragmentSettingsBinding

class FragmentHistory : Fragment() {
    private val binding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object{
        fun newInstance()= FragmentHistory().apply {
            arguments = Bundle().apply {
            }
        }
    }
}