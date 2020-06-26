package com.johncole.pianotracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johncole.pianotracker.R
import com.johncole.pianotracker.data.Session
import com.johncole.pianotracker.databinding.ListItemSessionBinding
import com.johncole.pianotracker.utilities.convertDurationToFormatted

class SessionListAdapter : ListAdapter<Session, SessionListAdapter.ViewHolder>(SessionDiffCallback()){

    class ViewHolder(private val binding: ListItemSessionBinding) : RecyclerView.ViewHolder(binding.root) {

        private val sessionDate: TextView = binding.sessionDateTextView
        private val sessionLength: TextView = binding.sessionLengthTextView
        private val sessionName: TextView = binding.sessionNameTextView

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

        //TODO: Use converters for these
        fun bind(item: Session) {
            val res = itemView.context.resources
            sessionDate.text = item.date.toString()
            sessionLength.text = convertDurationToFormatted(item.sessionLength, res)
            sessionName.text = item.sessionName
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