package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContestantsTableScreenViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : ContestantsTableScreenView(),
    ContestantsTableAdapter.Listener {

    private lateinit var fabAddContestant: FloatingActionButton
    private lateinit var progressBarContestantsTable: ProgressBar
    private lateinit var textViewNoContestantsRegistered: TextView

    private lateinit var recyclerViewContestantsTable: RecyclerView
    private lateinit var contestantsTableAdapter: ContestantsTableAdapter

    init {
        rootView = inflater.inflate(R.layout.contestants_table_screen, container, false)
        initializeViews()
        setupRecyclerView()
        fabAddContestant.setOnClickListener { onAddContestantButtonClicked() }
    }

    private fun initializeViews() {
        fabAddContestant = findViewById(R.id.fab_add_contestant)
        progressBarContestantsTable = findViewById(R.id.progress_bar_contestants_table)
        textViewNoContestantsRegistered = findViewById(R.id.text_view_no_contestants_registered)
        recyclerViewContestantsTable = findViewById(R.id.recycler_view_contestant_table)
    }

    private fun setupRecyclerView() {
        contestantsTableAdapter = ContestantsTableAdapter(this, viewFactory)
        recyclerViewContestantsTable.adapter = contestantsTableAdapter
    }

    override fun bindContestants(contestants: List<ASMRContestant>) {
        contestantsTableAdapter.submitData(contestants)
        if (contestants.isEmpty()) {
            recyclerViewContestantsTable.isVisible = false
            textViewNoContestantsRegistered.isVisible = true
        } else {
            recyclerViewContestantsTable.isVisible = true
            textViewNoContestantsRegistered.isVisible = false
        }
    }

    override fun showProgressIndicator() {
        progressBarContestantsTable.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarContestantsTable.isVisible = false
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        listeners.forEach { listener ->
            listener.onContestantClicked(contestant)
        }
    }

    private fun onAddContestantButtonClicked() {
        listeners.forEach { listener ->
            listener.onAddContestantButtonClicked()
        }
    }
}