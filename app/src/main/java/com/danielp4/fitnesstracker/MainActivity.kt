package com.danielp4.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danielp4.fitnesstracker.databinding.ActivityMainBinding
import com.danielp4.fitnesstracker.fragments.DaysFragment
import com.danielp4.fitnesstracker.utils.FragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(DaysFragment.newInstance(), this)
        setSupportActionBar(binding.toolbar)
    }

    override fun onBackPressed() {
        if (FragmentManager.currentFragment is DaysFragment) {
            super.onBackPressed()
        } else {
            FragmentManager.setFragment(DaysFragment.newInstance(), this)
        }
    }
}