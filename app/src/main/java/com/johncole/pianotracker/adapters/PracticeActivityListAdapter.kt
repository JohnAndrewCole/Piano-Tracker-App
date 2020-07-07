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
import com.johncole.pianotracker.data.PracticeActivity
import com.johncole.pianotracker.databinding.ListItemPracticeActivityBinding


class PracticeActivityListAdapter :
    ListAdapter<PracticeActivity, PracticeActivityListAdapter.ViewHolder>(
        PracticeActivityDiffCallback()
    ) {

    class ViewHolder(private val binding: ListItemPracticeActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.practiceActivity?.let { practiceActivity ->
                    openPracticeActivityDialog(practiceActivity, it)
                }
            }
        }

        private fun openPracticeActivityDialog(
            practiceActivity: PracticeActivity,
            view: View
        ) {
            view.findNavController()
                .navigate(
                    SessionFragmentDirections.actionSessionFragmentToNewPracticeActivityDialogFragment(
                        true,
                        practiceActivity.sessionId.toLong()
                    )
                )
        }

        fun bind(item: PracticeActivity) {
            binding.practiceActivity = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_practice_activity, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PracticeActivityDiffCallback : DiffUtil.ItemCallback<PracticeActivity>() {
    override fun areItemsTheSame(oldItem: PracticeActivity, newItem: PracticeActivity): Boolean {
        return oldItem.practiceActivityId == newItem.practiceActivityId
    }

    override fun areContentsTheSame(oldItem: PracticeActivity, newItem: PracticeActivity): Boolean {
        return oldItem == newItem
    }
}