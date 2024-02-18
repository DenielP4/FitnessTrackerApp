package com.danielp4.fitnesstracker.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielp4.fitnesstracker.adapters.ExerciseModel
import java.util.ArrayList

class MainViewModel : ViewModel() {

    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()

    var pref: SharedPreferences? = null

    var currentDay = 0
    var currentTraining = ""

    fun savePref(key: String, value: Int) {
        pref?.edit()?.putInt(key, value)?.apply()
    }

    fun getExerciseCount(): Int {
        return pref?.getInt(currentTraining.toString()+currentDay.toString(), 0) ?: 0
    }

}