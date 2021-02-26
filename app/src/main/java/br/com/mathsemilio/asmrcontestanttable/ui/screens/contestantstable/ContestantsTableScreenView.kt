package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContestantsTableScreenView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<ContestantsTableContract.View.Listener>(),
    ContestantsTableContract.View,
    ContestantsListAdapter.Listener {

    private lateinit var buttonAddContestant: FloatingActionButton
    private lateinit var progressBarContestantsTable: ProgressBar
    private lateinit var textViewNoContestantsRegistered: TextView

    private lateinit var recyclerViewContestantsTable: RecyclerView
    private lateinit var contestantsListAdapter: ContestantsListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.contestants_list_screen, container, false)
        initializeViews()
        setupRecyclerView(layoutInflater)
    }

    private fun initializeViews() {
        buttonAddContestant = findViewById(R.id.fab_add_contestant)
        progressBarContestantsTable = findViewById(R.id.progress_bar_contestants_table)
        textViewNoContestantsRegistered = findViewById(R.id.text_view_no_contestants_registered)
        recyclerViewContestantsTable = findViewById(R.id.recycler_view_contestant_table)
    }

    private fun setupRecyclerView(layoutInflater: LayoutInflater) {
        contestantsListAdapter = ContestantsListAdapter(layoutInflater, this)
        recyclerViewContestantsTable.adapter = contestantsListAdapter
    }

    override fun bindContestants(contestants: List<ASMRContestant>) {
        contestantsListAdapter.submitList(contestants)
        if (contestants.isEmpty()) {
            recyclerViewContestantsTable.visibility = View.GONE
            textViewNoContestantsRegistered.visibility = View.VISIBLE
        } else {
            recyclerViewContestantsTable.visibility = View.VISIBLE
            textViewNoContestantsRegistered.visibility = View.GONE
        }
    }

    override fun showProgressIndicator() {
        progressBarContestantsTable.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarContestantsTable.visibility = View.GONE
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        listeners.forEach { it.onContestantClicked(contestant) }
    }
}