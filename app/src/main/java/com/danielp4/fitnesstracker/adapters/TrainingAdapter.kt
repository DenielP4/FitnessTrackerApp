package com.danielp4.fitnesstracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielp4.fitnesstracker.R
import com.danielp4.fitnesstracker.databinding.DaysListItemBinding
import com.danielp4.fitnesstracker.databinding.TrainingSetListItemBinding
import pl.droidsonroids.gif.GifDrawable

class TrainingAdapter(
    var listener: Listener
) : ListAdapter<TrainingModel, TrainingAdapter.TrainingHolder>(MyComparator()) {

    class TrainingHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = TrainingSetListItemBinding.bind(view)
        fun setData(training: TrainingModel, listener: Listener) = with(binding) {
            val name = root.context.getString(R.string.training) + " ${training.trainig}"
            tvNameTrainig.text = name
            tvCounterDays.text = training.dayCount.toString()
            imgTraining.setImageDrawable(GifDrawable(root.context.assets, training.image))
            itemView.setOnClickListener {
                listener.onClick(training)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainingHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.training_set_list_item, parent, false)
        return TrainingHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class MyComparator : DiffUtil.ItemCallback<TrainingModel>() {
        override fun areItemsTheSame(oldItem: TrainingModel, newItem: TrainingModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TrainingModel, newItem: TrainingModel): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClick(training: TrainingModel)
    }
}