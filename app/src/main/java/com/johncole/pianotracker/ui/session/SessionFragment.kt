package com.johncole.pianotracker.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.johncole.pianotracker.R
import com.johncole.pianotracker.ui.dialog.NewPracticeActivityDialogFragment

class SessionFragment : Fragment() {
    private val viewModel: SessionViewModel by viewModels()

    companion object {
        fun newInstance() =
            SessionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_session, container, false)

        val newPracticeButton: ImageButton = layout.findViewById(R.id.add_new_practice_activity)
        newPracticeButton.setOnClickListener() {
            launchNewPracticeActivityDialog()
        }

        return layout
    }

    private fun launchNewPracticeActivityDialog() {
        val newFragment = NewPracticeActivityDialogFragment()
        newFragment.show(parentFragmentManager, "new_practice_activity_dialog")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}