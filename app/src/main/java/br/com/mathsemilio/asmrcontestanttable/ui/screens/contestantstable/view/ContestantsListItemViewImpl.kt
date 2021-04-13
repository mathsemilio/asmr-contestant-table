package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsListItemViewImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    ContestantsListItemView() {

    private lateinit var textViewContestantPosition: TextView
    private lateinit var textViewContestantName: TextView
    private lateinit var textViewContestantScore: TextView

    private lateinit var currentContestant: ASMRContestant

    init {
        rootView = inflater.inflate(R.layout.contestant_list_item, parent, false)
        rootView.setOnClickListener { onContestantListItemClicked(currentContestant) }
        initializeViews()
    }

    private fun initializeViews() {
        textViewContestantPosition = findViewById(R.id.text_view_contestant_position)
        textViewContestantName = findViewById(R.id.text_view_contestant_name)
        textViewContestantScore = findViewById(R.id.text_view_contestant_score)
    }

    override fun bindContestant(contestant: ASMRContestant, position: Int) {
        currentContestant = contestant
        textViewContestantPosition.text = position.toString()
        textViewContestantName.text = contestant.name
        textViewContestantScore.text = contestant.score.toString()
    }

    private fun onContestantListItemClicked(contestant: ASMRContestant) {
        listeners.forEach { listener ->
            listener.onContestantClicked(contestant)
        }
    }
}