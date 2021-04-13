package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.WeekHighlights
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.WeekHighlightsListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeekHighlightsViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : WeekHighlightsView() {

    private lateinit var fabAddWeekHighlights: FloatingActionButton
    private lateinit var progressBarWeekHighlightsList: ProgressBar
    private lateinit var textViewNoWeekHighlightsRegistered: TextView

    private lateinit var recyclerViewWeekHighlightsList: RecyclerView
    private lateinit var weekHighlightsListAdapter: WeekHighlightsListAdapter

    init {
        rootView = inflater.inflate(R.layout.week_highlights_list_screen, container, false)
        initializeViews()
        setupRecyclerView()
        fabAddWeekHighlights.setOnClickListener { onAddWeekHighlightsButtonClicked() }
    }

    private fun initializeViews() {
        fabAddWeekHighlights = findViewById(R.id.fab_add_week_highlights)
        progressBarWeekHighlightsList = findViewById(R.id.progress_bar_week_highlights_list)
        textViewNoWeekHighlightsRegistered = findViewById(R.id.text_view_no_week_highlights_registered)
        recyclerViewWeekHighlightsList = findViewById(R.id.recycler_view_week_highlights)
    }

    private fun setupRecyclerView() {
        weekHighlightsListAdapter = WeekHighlightsListAdapter(viewFactory)
        recyclerViewWeekHighlightsList.adapter = weekHighlightsListAdapter
    }

    override fun bindWeekHighlights(weekHighlights: List<WeekHighlights>) {
        weekHighlightsListAdapter.submitList(weekHighlights)
        if (weekHighlights.isEmpty()) {
            recyclerViewWeekHighlightsList.isVisible = false
            textViewNoWeekHighlightsRegistered.isVisible = true
        } else {
            recyclerViewWeekHighlightsList.isVisible = true
            textViewNoWeekHighlightsRegistered.isVisible = false
        }
    }

    override fun showProgressIndicator() {
        progressBarWeekHighlightsList.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarWeekHighlightsList.isVisible = false
    }

    private fun onAddWeekHighlightsButtonClicked() {
        listeners.forEach { listener ->
            listener.onAddWeekHighlightsButtonClicked()
        }
    }
}