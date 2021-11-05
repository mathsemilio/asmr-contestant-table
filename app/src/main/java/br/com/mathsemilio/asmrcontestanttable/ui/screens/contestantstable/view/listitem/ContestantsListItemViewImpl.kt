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

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.getDrawableResource
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantsListItemViewImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    ContestantsListItemView() {

    private var imageViewContestantProfilePicture: ImageView
    private var textViewContestantPosition: TextView
    private var textViewContestantName: TextView
    private var textViewTimesSlept: TextView
    private var textViewTimesDidNotSlept: TextView
    private var textViewTimesFeltTired: TextView
    private var textViewContestantPoints: TextView

    private lateinit var currentContestant: ASMRContestant

    init {
        rootView = inflater.inflate(R.layout.contestant_list_item, parent, false)

        imageViewContestantProfilePicture = findViewById(R.id.image_view_contestant_profile_picture)
        textViewContestantPosition = findViewById(R.id.text_view_contestant_position)
        textViewContestantName = findViewById(R.id.text_view_contestant_name)
        textViewTimesSlept = findViewById(R.id.text_view_times_slept)
        textViewTimesDidNotSlept = findViewById(R.id.text_view_times_did_not_slept)
        textViewTimesFeltTired = findViewById(R.id.text_view_times_felt_tired)
        textViewContestantPoints = findViewById(R.id.text_view_contestant_points)

        rootView.setOnClickListener {
            onContestantListItemClicked(currentContestant)
        }
    }

    override fun bindContestant(contestant: ASMRContestant, position: Int) {
        currentContestant = contestant

        textViewContestantPosition.text = position.toString()

        bindContestantProfilePicture(contestant.profilePicture)

        textViewContestantName.text = contestant.name

        textViewTimesSlept.text = contestant.timesSlept.toString()

        textViewTimesDidNotSlept.text = contestant.timesDidNotSlept.toString()

        textViewTimesFeltTired.text = contestant.timesFeltTired.toString()

        textViewContestantPoints.text = contestant.score.toString()
    }

    private fun bindContestantProfilePicture(profilePicture: String) {
        if (profilePicture != "")
            imageViewContestantProfilePicture.setImageURI(Uri.parse(profilePicture))
        else
            imageViewContestantProfilePicture.setImageDrawable(
                context.getDrawableResource(R.drawable.dr_contestant_profile_picture)
            )
    }

    private fun onContestantListItemClicked(contestant: ASMRContestant) {
        notifyListener { it.onContestantClicked(contestant) }
    }
}