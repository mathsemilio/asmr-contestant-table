package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.BaseObservableView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeekHighlightsListScreenView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<WeekHighlightsContract.ListScreen.Listener>(),
    WeekHighlightsContract.ListScreen {

    private lateinit var buttonAddWeekHighlights: FloatingActionButton
    private lateinit var progressBarWeekHighlightsList: ProgressBar
    private lateinit var textViewNoWeekHighlightsRegistered: TextView

    private lateinit var recyclerViewWeekHighlightsList: RecyclerView
    private lateinit var weekHighlightsListAdapter: WeekHighlightsListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.contestants_list_screen, container, false)
        initializeViews()
        setupRecyclerView(layoutInflater)
    }

    private fun initializeViews() {
        buttonAddWeekHighlights = findViewById(R.id.fab_add_contestant)
        progressBarWeekHighlightsList = findViewById(R.id.progress_bar_contestants_table)
        textViewNoWeekHighlightsRegistered = findViewById(R.id.text_view_no_contestants_registered)
        recyclerViewWeekHighlightsList = findViewById(R.id.recycler_view_contestant_table)
    }

    private fun setupRecyclerView(layoutInflater: LayoutInflater) {
        weekHighlightsListAdapter = WeekHighlightsListAdapter(layoutInflater)
        recyclerViewWeekHighlightsList.adapter = weekHighlightsListAdapter
    }

    override fun bindWeekHighlights(weekHighlights: List<WeekHighlights>) {
        weekHighlightsListAdapter.submitList(weekHighlights)
        if (weekHighlights.isEmpty()) {
            recyclerViewWeekHighlightsList.visibility = View.GONE
            textViewNoWeekHighlightsRegistered.visibility = View.VISIBLE
        } else {
            recyclerViewWeekHighlightsList.visibility = View.VISIBLE
            textViewNoWeekHighlightsRegistered.visibility = View.GONE
        }
    }

    override fun showProgressIndicator() {
        progressBarWeekHighlightsList.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarWeekHighlightsList.visibility = View.GONE
    }
}