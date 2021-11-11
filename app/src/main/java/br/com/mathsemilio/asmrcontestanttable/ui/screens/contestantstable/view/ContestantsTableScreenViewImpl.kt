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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.MIN_SCROLL_Y_VALUE
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant
import br.com.mathsemilio.asmrcontestanttable.ui.common.view.ViewFactory
import br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.ContestantsTableAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContestantsTableScreenViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?,
    private val viewFactory: ViewFactory
) : ContestantsTableScreenView(), ContestantsTableAdapter.Listener {

    private lateinit var fabAddContestant: FloatingActionButton
    private lateinit var progressBarContestantsTable: ProgressBar
    private lateinit var linearLayoutNoContestantsFoundState: LinearLayout
    private lateinit var constraintLayoutCellContentDescriptionContainer: ConstraintLayout

    private lateinit var recyclerViewContestantsTable: RecyclerView
    private lateinit var contestantsTableAdapter: ContestantsTableAdapter

    init {
        rootView = inflater.inflate(R.layout.contestants_table_screen, container, false)

        initializeViews()

        setupRecyclerView()

        attachRecyclerViewScrollListener()

        attachAddContestantButtonOnClickListener()
    }

    private fun initializeViews() {
        fabAddContestant =
            findViewById(R.id.fab_add_contestant)
        progressBarContestantsTable =
            findViewById(R.id.progress_bar_contestants_table)
        linearLayoutNoContestantsFoundState =
            findViewById(R.id.linear_layout_no_contestants_found_state)
        constraintLayoutCellContentDescriptionContainer =
            findViewById(R.id.constraint_layout_cell_content_description_container)
        recyclerViewContestantsTable =
            findViewById(R.id.recycler_view_contestant_table)
    }

    private fun setupRecyclerView() {
        contestantsTableAdapter = ContestantsTableAdapter(this, viewFactory)
        recyclerViewContestantsTable.adapter = contestantsTableAdapter
    }

    private fun attachRecyclerViewScrollListener() {
        recyclerViewContestantsTable.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy >= MIN_SCROLL_Y_VALUE)
                        fabAddContestant.hide()
                    else if (dy < 0)
                        fabAddContestant.show()
                }
            }
        )
    }

    private fun attachAddContestantButtonOnClickListener() {
        fabAddContestant.setOnClickListener {
            notify { listener ->
                listener.onAddContestantButtonClicked()
            }
        }
    }

    override fun bindContestants(contestants: List<ASMRContestant>) {
        contestantsTableAdapter.submitData(contestants)

        if (contestants.isEmpty()) {
            recyclerViewContestantsTable.isVisible = false
            linearLayoutNoContestantsFoundState.isVisible = true
            constraintLayoutCellContentDescriptionContainer.isVisible = false
        } else {
            recyclerViewContestantsTable.isVisible = true
            linearLayoutNoContestantsFoundState.isVisible = false
            constraintLayoutCellContentDescriptionContainer.isVisible = true
        }
    }

    override fun showProgressIndicator() {
        progressBarContestantsTable.isVisible = true
    }

    override fun hideProgressIndicator() {
        progressBarContestantsTable.isVisible = false
    }

    override fun onContestantClicked(contestant: ASMRContestant) {
        notify { listener ->
            listener.onContestantClicked(contestant)
        }
    }
}
