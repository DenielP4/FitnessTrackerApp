package com.danielp4.fitnesstracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.danielp4.fitnesstracker.utils.DialogManager
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel
import com.danielp4.fitnesstracker.utils.Resource
import java.util.ArrayList


class DaysFragment : Fragment(), DaysAdapter.Listener {

    private lateinit var binding: FragmentDaysBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null

    private lateinit var dayAdapter: DaysAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentDay = 0
        actionBar = Resource.setActionBar(R.string.name_sport, (activity as AppCompatActivity))
        initRcView()
    }

    private fun initRcView() = with(binding) {
        dayAdapter = DaysAdapter(this@DaysFragment)
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
        val nameArray = viewModel.currentTraining
        when(nameArray) {
            "0" -> {
                resources.getStringArray(R.array.day_exercise_0).forEach { day ->
                    val dayModel = fill(day, tArray)
                    tArray.add(dayModel)
                }
            }
            "1" -> {
                resources.getStringArray(R.array.day_exercise_1).forEach { day ->
                    val dayModel = fill(day, tArray)
                    tArray.add(dayModel)
                }
            }
            "2" -> {
                resources.getStringArray(R.array.day_exercise_2).forEach { day ->
                    val dayModel = fill(day, tArray)
                    tArray.add(dayModel)
                }
            }
//            "3" -> {
//                resources.getStringArray(R.array.day_exercise_3).forEach { day ->
//                    val dayModel = fill(day, tArray)
//                    tArray.add(dayModel)
//                }
//            }
//            "4" -> {
//                resources.getStringArray(R.array.day_exercise_4).forEach { day ->
//                    val dayModel = fill(day, tArray)
//                    tArray.add(dayModel)
//                }
//            }
        }

        binding.progressBar.max = tArray.size
        updateRestDaysUI(tArray)
        return tArray
    }

    private fun fill(day: String, tArray: ArrayList<DayModel>): DayModel {
        viewModel.currentDay++
        val exCounter = day.split(",").size
        val dayModel = DayModel(
            exercises = day,
            dayNumber = tArray.size + 1,
            isDone = viewModel.getExerciseCount() == exCounter,
        )
        return dayModel
    }

    private fun updateRestDaysUI(tArray: ArrayList<DayModel>) {
        val continueDay = tArray.filter { !it.isDone }.size.toString()
        binding.apply {
            val restDays = getString(R.string.rest) + " " + continueDay + " " + getString(R.string.days)
            tvRestDays.text = restDays
            progressBar.progress = tArray.size - continueDay.toInt()
        }
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
                    image = exerciseArray[2],
                    isDone = false
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
        if (day.isDone) {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_day_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        viewModel.savePref(viewModel.currentTraining+day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        viewModel.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }
                }
            )
        } else {
            fillExerciseList(day)
            viewModel.currentDay = day.dayNumber
            Log.d("MyLog", "$day")
            FragmentManager.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_days_message,
                object : DialogManager.Listener {
                    override fun onClick() {
                        viewModel.pref?.edit()?.clear()?.apply()
                        dayAdapter.submitList(fillDaysArray())
                    }
                }
            )

        }
        return super.onOptionsItemSelected(item)
    }

}