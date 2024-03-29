package com.johncole.pianotracker

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.johncole.pianotracker.adapters.SessionListAdapter
import com.johncole.pianotracker.databinding.FragmentSessionListBinding
import com.johncole.pianotracker.utilities.InjectorUtils
import com.johncole.pianotracker.utilities.setDivider
import com.johncole.pianotracker.viewmodels.HomeScreensViewModel

class SessionListFragment : Fragment() {

    private val viewModel: HomeScreensViewModel by viewModels {
        InjectorUtils.provideHomeScreensViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding = FragmentSessionListBinding.inflate(inflater, container, false)
        val adapter = SessionListAdapter()
        binding.sessionListRecyclerView.let {
            it.adapter = adapter
            it.setDivider(R.drawable.recycler_view_divider)
        }

        viewModel.sessions.observe(
            viewLifecycleOwner,
            { result ->
                binding.hasSessions = !result.isNullOrEmpty()
                adapter.submitList(result)
            }
        )

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_screens_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            getString(R.string.menu_item_about) -> {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setTitle(R.string.app_name)
                        setMessage(getString(R.string.description, BuildConfig.VERSION_NAME))
                        setPositiveButton(
                            R.string.close
                        ) { dialog, _ ->
                            dialog.dismiss()
                        }
                    }

                    // Create the AlertDialog
                    builder.create()
                }
                alertDialog?.show()
            }
        }
        return false
    }
}
