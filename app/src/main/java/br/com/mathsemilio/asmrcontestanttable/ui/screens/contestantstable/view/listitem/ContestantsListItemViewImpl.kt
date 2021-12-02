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
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsListItemViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : ContestantsListItemView() {

    private lateinit var imageViewContestantProfilePicture: ImageView
    private lateinit var textViewContestantPosition: TextView
    private lateinit var textViewContestantName: TextView
    private lateinit var textViewTimesSlept: TextView
    private lateinit var textViewTimesDidNotSlept: TextView
    private lateinit var textViewTimesFeltTired: TextView
    private lateinit var textViewContestantPoints: TextView

    private lateinit var contestant: ASMRContestant

    init {
        rootView = inflater.inflate(R.layout.contestant_list_item, parent, false)

        initializeViews()

        rootView.setOnClickListener {
            onContestantListItemClicked(contestant)
        }
    }

    private fun initializeViews() {
        imageViewContestantProfilePicture = findViewById(R.id.image_view_contestant_profile_picture)
        textViewContestantPosition = findViewById(R.id.text_view_contestant_position)
        textViewContestantName = findViewById(R.id.text_view_contestant_name)
        textViewTimesSlept = findViewById(R.id.text_view_times_slept)
        textViewTimesDidNotSlept = findViewById(R.id.text_view_times_did_not_slept)
        textViewTimesFeltTired = findViewById(R.id.text_view_times_felt_tired)
        textViewContestantPoints = findViewById(R.id.text_view_contestant_points)
    }

    private fun onContestantListItemClicked(contestant: ASMRContestant) {
        notify { listener ->
            listener.onContestantClicked(contestant)
        }
    }

    override fun bindContestant(contestant: ASMRContestant, position: Int) {
        this.contestant = contestant

        textViewContestantPosition.text = position.toString()

        textViewContestantName.text = contestant.name
        textViewTimesSlept.text = contestant.timesSlept.toString()
        textViewTimesDidNotSlept.text = contestant.timesDidNotSlept.toString()
        textViewTimesFeltTired.text = contestant.timesFeltTired.toString()
        textViewContestantPoints.text = contestant.score.toString()
    }
}
