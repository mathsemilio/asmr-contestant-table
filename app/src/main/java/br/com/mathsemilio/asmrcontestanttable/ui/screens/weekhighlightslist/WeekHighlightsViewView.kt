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

class WeekHighlightsViewView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<WeekHighlightsContract.View.Listener>(),
    WeekHighlightsContract.View {

    private lateinit var buttonAddWeekHighlights: FloatingActionButton
    private lateinit var progressBarWeekHighlightsList: ProgressBar
    private lateinit var textViewNoWeekHighlightsRegistered: TextView

    private lateinit var recyclerViewWeekHighlightsList: RecyclerView
    private lateinit var weekHighlightsListAdapter: WeekHighlightsListAdapter

    init {
        rootView = layoutInflater.inflate(R.layout.week_highlights_list_screen, container, false)
        initializeViews()
        setupRecyclerView(layoutInflater)
        buttonAddWeekHighlights.setOnClickListener { onAddWeekHighlightsButtonClicked() }
    }

    private fun initializeViews() {
        buttonAddWeekHighlights = findViewById(R.id.fab_add_week_highlights)
        progressBarWeekHighlightsList = findViewById(R.id.progress_bar_week_highlights_list)
        textViewNoWeekHighlightsRegistered = findViewById(R.id.text_view_no_week_highlights_registered)
        recyclerViewWeekHighlightsList = findViewById(R.id.recycler_view_week_highlights)
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

    private fun onAddWeekHighlightsButtonClicked() {
        listeners.forEach { it.onAddButtonClicked() }
    }

    override fun showProgressIndicator() {
        progressBarWeekHighlightsList.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progressBarWeekHighlightsList.visibility = View.GONE
    }
}