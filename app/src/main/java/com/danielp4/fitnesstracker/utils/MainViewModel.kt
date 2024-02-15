package com.danielp4.fitnesstracker.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielp4.fitnesstracker.adapters.ExerciseModel
import java.util.ArrayList

class MainViewModel : ViewModel() {

    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()

}