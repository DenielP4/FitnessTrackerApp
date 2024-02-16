package com.danielp4.fitnesstracker.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielp4.fitnesstracker.R
import com.danielp4.fitnesstracker.databinding.DaysListItemBinding
import com.danielp4.fitnesstracker.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExercisesAdapter : ListAdapter<ExerciseModel, ExercisesAdapter.ExercizeHolder>(MyComparator()) {

    class ExercizeHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ExerciseListItemBinding.bind(view)
        fun setData(exercise: ExerciseModel) = with(binding) {
            tvName.text = exercise.name
            tvCount.text = exercise.time
            chB.isChecked = exercise.isDone
            imgEx.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercizeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)
        return ExercizeHolder(view)
    }

    override fun onBindViewHolder(holder: ExercizeHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class MyComparator : DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

    }

}