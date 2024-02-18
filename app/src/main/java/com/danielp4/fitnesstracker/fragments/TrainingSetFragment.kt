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
import com.danielp4.fitnesstracker.adapters.TrainingAdapter
import com.danielp4.fitnesstracker.adapters.TrainingModel
import com.danielp4.fitnesstracker.databinding.FragmentDaysBinding
import com.danielp4.fitnesstracker.databinding.FragmentTrainingSetBinding
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel
import com.danielp4.fitnesstracker.utils.Resource
import java.util.ArrayList


class TrainingSetFragment : Fragment(), TrainingAdapter.Listener {

    private lateinit var binding: FragmentTrainingSetBinding
    private lateinit var trainingAdapter: TrainingAdapter
    private val viewModel: MainViewModel by activityViewModels()
    private var actionBar: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingSetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBar = Resource.setActionBar(R.string.app_name, (activity as AppCompatActivity))
        initRcView()
    }

    private fun initRcView() = with(binding) {
        trainingAdapter = TrainingAdapter(this@TrainingSetFragment)
        rcTraining.apply {
            layoutManager = LinearLayoutManager(activity) // as App
            adapter = trainingAdapter
        }
        trainingAdapter.submitList(fillTrainingArray())
    }

    private fun fillTrainingArray(): ArrayList<TrainingModel> {
        val tArray = ArrayList<TrainingModel>()
        resources.getStringArray(R.array.training).forEach { training ->
            val infoTraining = training.split("|")
            val trainingModel = TrainingModel(
                trainig = infoTraining[0],
                dayCount = infoTraining[1],
                image = infoTraining[2],
                id = infoTraining[3]
            )
            tArray.add(trainingModel)
        }
        return tArray
    }

    companion object {
        @JvmStatic
        fun newInstance() = TrainingSetFragment()
    }

    override fun onClick(training: TrainingModel) {
        viewModel.currentTraining = training.id
        FragmentManager.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
    }
}