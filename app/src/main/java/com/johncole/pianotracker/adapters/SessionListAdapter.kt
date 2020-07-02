package com.johncole.pianotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johncole.pianotracker.R
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.databinding.ListItemSessionBinding

class SessionListAdapter : ListAdapter<Session, SessionListAdapter.ViewHolder>(SessionDiffCallback()){

    class ViewHolder(private val binding: ListItemSessionBinding) : RecyclerView.ViewHolder(binding.root) {

        //TODO: Implement navigation
//        init {
//            binding.setClickListener { view ->
//                binding.viewModel?.sessionId?.let { sessionId ->
//                    navigateToSession(sessionId, view)
//                }
//            }
//        }
//
//        private fun navigateToSession(sessionId: Long, view: View) {
//            val direction = NavHostFragment.(plantId)
//            view.findNavController().navigate(direction)
//        }

        fun bind(item: Session) {
            binding.session = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_session, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SessionDiffCallback : DiffUtil.ItemCallback<Session>() {
    override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
        return oldItem.sessionId == newItem.sessionId
    }

    override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
        return oldItem == newItem
    }
}