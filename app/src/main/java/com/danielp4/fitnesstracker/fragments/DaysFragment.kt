package com.danielp4.fitnesstracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielp4.fitnesstracker.R
import com.danielp4.fitnesstracker.adapters.DayModel
import com.danielp4.fitnesstracker.adapters.DaysAdapter
import com.danielp4.fitnesstracker.adapters.ExerciseModel
import com.danielp4.fitnesstracker.databinding.FragmentDaysBinding
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel
import com.danielp4.fitnesstracker.utils.Resource
import java.util.ArrayList


class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = Resource.setActionBar(R.string.name_sport, (activity as AppCompatActivity))
        initRcView()
    }

    private fun initRcView() = with(binding) {
        val dayAdapter = DaysAdapter(this@DaysFragment)
        rcViewDays.apply {
            layoutManager = LinearLayoutManager(activity) // as App
            adapter = dayAdapter
        }
        Log.d("MyLog", "${rcViewDays.adapter}")
        dayAdapter.submitList(fillDaysArray())
        Log.d("MyLog", "${rcViewDays.adapter}")

    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        val tArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercise).forEach { day ->
            val dayModel = DayModel(
                exercises = day,
                isDone = false
            )
            tArray.add(dayModel)
        }
        return tArray
    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach { ex ->
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[ex.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(
                ExerciseModel(
                    name = exerciseArray[0],
                    time = exerciseArray[1],
                    image = exerciseArray[2]
                )
            )
        }
        viewModel.mutableListExercise.value = tempList

    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        fillExerciseList(day)
        FragmentManager.setFragment(ExerciseListFragment.newInstance(), activity as AppCompatActivity)
    }


}