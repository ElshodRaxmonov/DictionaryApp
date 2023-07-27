package com.example.dictionaryapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.dictionaryapp.R
import com.example.dictionaryapp.ui.fragment.FragmentFavorite
import com.example.dictionaryapp.ui.fragment.FragmentHistory
import com.example.dictionaryapp.ui.fragment.FragmentHome
import com.example.dictionaryapp.ui.fragment.FragmentQuiz
import com.example.dictionaryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

                        override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(binding.root)

                    binding.navBar.add(MeowBottomNavigation.Model(1, R.drawable.heart_24))
                    binding.navBar.add(MeowBottomNavigation.Model(2, R.drawable.home_24))
                    binding.navBar.add(MeowBottomNavigation.Model(3, R.drawable.lightbulb_question_24))
                    binding.navBar.add(MeowBottomNavigation.Model(4, R.drawable.settings_24))
                    binding.navBar.setOnClickMenuListener { model: MeowBottomNavigation.Model? ->

                        when (model!!.id) {
                            1 -> replaceFragment(FragmentFavorite.newInstance())
                            2 -> replaceFragment(FragmentHome.newInstance())
                            4 -> replaceFragment(FragmentHistory.newInstance())
                3 -> replaceFragment(FragmentQuiz.newInstance())
            }

        }

//        binding.navBar.setOnShowListener { model: MeowBottomNavigation.Model? ->
//            when (model!!.id) {
//                1 -> replaceFragment(FragmentFavorite.newInstance())
//                2 -> replaceFragment(FragmentHome.newInstance())
//                3 -> replaceFragment(FragmentHistory.newInstance())
//
//            }
//        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.nav_host_fragment, fragment).commit()

    }
}