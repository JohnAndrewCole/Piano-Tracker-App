package com.johncole.pianotracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johncole.pianotracker.R
import com.johncole.pianotracker.SessionFragmentDirections
import com.johncole.pianotracker.data.Goal
import com.johncole.pianotracker.databinding.ListItemGoalBinding

class GoalListAdapter :
    ListAdapter<Goal, GoalListAdapter.ViewHolder>(
        GoalDiffCallback()
    ) {

    class ViewHolder(private val binding: ListItemGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.goal?.let { practiceActivity ->
                    openGoalDialog(practiceActivity, it)
                }
            }
        }

        private fun openGoalDialog(
            goal: Goal,
            view: View
        ) {
            view.findNavController()
                .navigate(
                    SessionFragmentDirections.actionSessionFragmentToGoalDialogFragment(
                        true,
                        goal.sessionId.toLong(),
                        goal.goalId
                    )
                )
        }

        fun bind(item: Goal) {
            binding.goal = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_goal,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem.goalId == newItem.goalId
    }

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem == newItem
    }
}
