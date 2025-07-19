package com.example.islami.hom

import RadioFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.islami.R
import com.example.islami.databinding.ActivityMainBinding
import com.example.islami.hom.tabs.hadeth.HadethFragment
import com.example.islami.hom.tabs.quran.QuranFragment
import com.example.islami.hom.tabs.spha.sphaFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNveView.setOnItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.navigation_quran -> {
                    QuranFragment()

                }

                R.id.navigation_hadeth -> {
                    HadethFragment()

                }

                R.id.navigation_spha -> {
                    sphaFragment()

                }

                R.id.navigation_radio -> {
                    RadioFragment()
                }

                else -> {
                    QuranFragment()
                }
            }

            ShowFragment(fragment)
            return@setOnItemSelectedListener true
        }
        binding.bottomNveView.selectedItemId = R.id.navigation_quran

    }

    fun ShowFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()

    }

}