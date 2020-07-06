package com.johncole.pianotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.johncole.pianotracker.adapters.SessionListAdapter
import com.johncole.pianotracker.databinding.FragmentSessionListBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.viewmodels.SessionListViewModel

class SessionListFragment : Fragment() {

    private lateinit var binding: FragmentSessionListBinding

    private val viewModel: SessionListViewModel by viewModels {
        InjectorUtils.provideSessionListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionListBinding.inflate(inflater, container, false)
        val adapter = SessionListAdapter()
        binding.sessionListRecyclerView.adapter = adapter

        viewModel.sessions.observe(viewLifecycleOwner) { result ->
            binding.hasSessions = !result.isNullOrEmpty()
            adapter.submitList(result)
        }

        binding.fabNewSession.setOnClickListener { view ->
            view.findNavController()
                .navigate(
                    SessionListFragmentDirections.actionSessionListFragmentToSessionFragment(
                        false,
                        -1
                    )
                )
        }

        return binding.root
    }
}