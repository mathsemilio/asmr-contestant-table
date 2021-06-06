/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.asmrcontestanttable.ui.screens.weekhighlightslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
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
    private lateinit var linearLayoutNoWeekHighlightsFoundState: LinearLayout

    private lateinit var recyclerViewWeekHighlightsList: RecyclerView
    private lateinit var weekHighlightsListAdapter: WeekHighlightsListAdapter

    init {
        rootView = inflater.inflate(R.layout.week_highlights_list_screen, container, false)

        initializeViews()

        setupRecyclerView()

        fabAddWeekHighlights.setOnClickListener {
            notifyListener { it.onAddWeekHighlightsButtonClicked() }
        }
    }

    private fun initializeViews() {
        fabAddWeekHighlights =
            findViewById(R.id.fab_add_week_highlights)
        progressBarWeekHighlightsList =
            findViewById(R.id.progress_bar_week_highlights_list)
        linearLayoutNoWeekHighlightsFoundState =
            findViewById(R.id.linear_layout_no_week_highlights_found_state)
        recyclerViewWeekHighlightsList =
            findViewById(R.id.recycler_view_week_highlights)
    }

    private fun setupRecyclerView() {
        weekHighlightsListAdapter = WeekHighlightsListAdapter(viewFactory)
        recyclerViewWeekHighlightsList.adapter = weekHighlightsListAdapter
    }

    override fun bindWeekHighlights(weekHighlights: List<WeekHighlights>) {
        weekHighlightsListAdapter.submitList(weekHighlights)

        if (weekHighlights.isEmpty()) {
            recyclerViewWeekHighlightsList.isVisible = false
            linearLayoutNoWeekHighlightsFoundState.isVisible = true
        } else {
            recyclerViewWeekHighlightsList.isVisible = true
            linearLayoutNoWeekHighlightsFoundState.isVisible = false
        }
    }

    override fun showProgressIndicator() {
        progressBarWeekHighlightsList.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarWeekHighlightsList.isVisible = false
    }
}