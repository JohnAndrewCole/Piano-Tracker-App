package com.johncole.pianotracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.johncole.pianotracker.utilities.PRACTICE_ACTIVITY_BUNDLE_KEY
import com.johncole.pianotracker.utilities.SESSION_FRAGMENT_REQUEST_CODE
import kotlinx.android.synthetic.main.fragment_session.*

class SessionFragment : Fragment() {

    private var doesNewSessionExist: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_session, container, false)

        val newPracticeButton: ImageButton = layout.findViewById(R.id.add_new_practice_activity)
        newPracticeButton.setOnClickListener {
            launchNewPracticeActivityDialog()
        }

        return layout
    }
    private fun launchNewPracticeActivityDialog() {
        val newFragment = NewPracticeActivityDialogFragment()
        newFragment.setTargetFragment(this, SESSION_FRAGMENT_REQUEST_CODE)
        newFragment.show(parentFragmentManager, "new_practice_activity_dialog")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SESSION_FRAGMENT_REQUEST_CODE) {
            val result = data?.getBooleanExtra(PRACTICE_ACTIVITY_BUNDLE_KEY, false)
            if (result != null) {
                doesNewSessionExist = result
            }
        }

        if (doesNewSessionExist) {
            add_new_practice_activity.visibility = View.GONE
        }
    }
}