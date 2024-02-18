package com.danielp4.fitnesstracker.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.danielp4.fitnesstracker.R
import com.danielp4.fitnesstracker.adapters.ExerciseModel
import com.danielp4.fitnesstracker.databinding.ExerciseBinding
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel
import com.danielp4.fitnesstracker.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExerciseFragment : Fragment() {

    private lateinit var binding: ExerciseBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var timer: CountDownTimer? = null

    private var exerciseCounter = 0
    private var exList: ArrayList<ExerciseModel>? = null

    private var actionBar: ActionBar? = null

    private var currentDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = viewModel.currentDay
        Log.d("MyLog", "${viewModel.getExerciseCount()}")
        exerciseCounter = viewModel.getExerciseCount()
        actionBar = (activity as AppCompatActivity).supportActionBar
        viewModel.mutableListExercise.observe(viewLifecycleOwner) { exerciseList ->
            exList = exerciseList
            Log.d("MyLog", "$exerciseCounter")
            Log.d("MyLog", "${exList?.size!!}")
            nextExercise()
        }
        binding.btnNext.setOnClickListener {
            nextExercise()
        }

    }

    private fun nextExercise() {
        timer?.cancel()
        if (exerciseCounter < exList?.size!!) {
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex)
            showNextExercise()
        } else {
            exerciseCounter++
            FragmentManager.setFragment(DayFinishFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    private fun showExercise(exercise: ExerciseModel) = with(binding) {
        imgMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        tvName.text = exercise.name
        val title = "$exerciseCounter / ${exList?.size}"
        actionBar?.title = title
    }

    private fun showNextExercise()  = with(binding) {
        if (exerciseCounter < exList?.size!!) {
            val ex = exList?.get(exerciseCounter) ?: return
            imgNext.setImageDrawable(GifDrawable(root.context.assets, ex.image))
            setTimeType(ex)
        } else {
            imgNext.setImageDrawable(GifDrawable(root.context.assets, "love-chill.gif"))
            tvNextName.text = root.context.getString(R.string.done)
        }
    }

    private fun setTimeType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            val tvName = exercise.name +": " + exercise.time.removePrefix("x") + " раз"
            binding.tvNextName.text = tvName
        } else {
            val tvName = exercise.name +": " + TimeUtils.getTime(exercise.time.toLong() * 1000)
            binding.tvNextName.text = tvName
        }
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            val tvTime = exercise.time.removePrefix("x") + " раз"
            binding.tvTime.text = tvTime
            binding.pBar.visibility = View.INVISIBLE
        } else {
            binding.pBar.visibility = View.VISIBLE
            startTimer(exercise.time.toInt())
        }
    }

    private fun startTimer(time: Int) = with(binding) {
        val countDownTime = time * 1000L
        pBar.max = countDownTime.toInt()
        timer?.cancel()
        timer = object : CountDownTimer(countDownTime, 1) {
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()
            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.savePref(viewModel.currentTraining+currentDay.toString(), exerciseCounter-1)
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}