package com.danielp4.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.danielp4.fitnesstracker.databinding.ActivityMainBinding
import com.danielp4.fitnesstracker.fragments.DaysFragment
import com.danielp4.fitnesstracker.fragments.ExerciseListFragment
import com.danielp4.fitnesstracker.fragments.TrainingSetFragment
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(TrainingSetFragment.newInstance(), this)
        setSupportActionBar(binding.toolbar)
    }

    override fun onBackPressed() {
//        if (FragmentManager.currentFragment is TrainingSetFragment) {
//            super.onBackPressed()
//        } else  {
//            FragmentManager.setFragment(TrainingSetFragment.newInstance(), this)
//        }
        when(FragmentManager.currentFragment) {
            is TrainingSetFragment -> super.onBackPressed()
            is DaysFragment -> FragmentManager.setFragment(TrainingSetFragment.newInstance(), this)
            else -> FragmentManager.setFragment(DaysFragment.newInstance(), this)
        }
    }
}