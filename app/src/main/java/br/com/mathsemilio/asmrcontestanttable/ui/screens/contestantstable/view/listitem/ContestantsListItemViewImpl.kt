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

package br.com.mathsemilio.asmrcontestanttable.ui.screens.contestantstable.view.listitem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsListItemViewImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    ContestantsListItemView() {

    private var textViewContestantPosition: TextView
    private var textViewContestantName: TextView
    private var textViewContestantScore: TextView

    private lateinit var currentContestant: ASMRContestant

    init {
        rootView = inflater.inflate(R.layout.contestant_list_item, parent, false)

        textViewContestantPosition = findViewById(R.id.text_view_contestant_position)
        textViewContestantName = findViewById(R.id.text_view_contestant_name)
        textViewContestantScore = findViewById(R.id.text_view_contestant_score)

        rootView.setOnClickListener {
            onContestantListItemClicked(currentContestant)
        }
    }

    override fun bindContestant(contestant: ASMRContestant, position: Int) {
        currentContestant = contestant
        textViewContestantPosition.text = position.toString()
        textViewContestantName.text = contestant.name
        textViewContestantScore.text = contestant.score.toString()
    }

    private fun onContestantListItemClicked(contestant: ASMRContestant) {
        notifyListener { it.onContestantClicked(contestant) }
    }
}