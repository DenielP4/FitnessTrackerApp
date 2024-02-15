package com.danielp4.fitnesstracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielp4.fitnesstracker.adapters.ExercisesAdapter
import com.danielp4.fitnesstracker.databinding.ExerciseListFragmentBinding
import com.danielp4.fitnesstracker.utils.FragmentManager
import com.danielp4.fitnesstracker.utils.MainViewModel


class ExerciseListFragment : Fragment() {

    private lateinit var binding: ExerciseListFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var exAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.mutableListExercise.observe(viewLifecycleOwner) { exerciseList ->
            Log.d("MyLog", "$exerciseList")
            exAdapter.submitList(exerciseList)
        }
    }

    private fun init() = with(binding) {
        exAdapter = ExercisesAdapter()
        rcView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = exAdapter
        }
        btnStart.setOnClickListener {
            FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}