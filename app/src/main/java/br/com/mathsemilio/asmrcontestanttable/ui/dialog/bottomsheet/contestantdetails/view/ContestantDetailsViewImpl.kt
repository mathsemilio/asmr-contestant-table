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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.common.getDrawableResource
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantDetailsViewImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : ContestantDetailsView() {

    private lateinit var imageViewContestantProfilePicture: ImageView
    private lateinit var textViewContestantsDetailsName: TextView
    private lateinit var textViewTimesSlept: TextView
    private lateinit var textViewTimesDidNotSlept: TextView
    private lateinit var textViewTimesFeltTired: TextView
    private lateinit var buttonIncrementTimesSlept: ImageButton
    private lateinit var buttonIncrementTimesDidNotSlept: ImageButton
    private lateinit var buttonIncrementTimesFeltTired: ImageButton

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_contestant_details, container, false)

        initializeViews()

        attachClickListeners()
    }

    private fun initializeViews() {
        imageViewContestantProfilePicture = findViewById(R.id.image_view_contestant_profile_picture)
        textViewContestantsDetailsName = findViewById(R.id.text_view_contestants_details_name)
        textViewTimesSlept = findViewById(R.id.text_view_times_slept)
        textViewTimesDidNotSlept = findViewById(R.id.text_view_times_did_not_slept)
        textViewTimesFeltTired = findViewById(R.id.text_view_times_felt_tired)
        buttonIncrementTimesSlept = findViewById(R.id.button_increment_times_slept)
        buttonIncrementTimesDidNotSlept = findViewById(R.id.button_increment_times_did_not_slept)
        buttonIncrementTimesFeltTired = findViewById(R.id.button_increment_times_felt_tired)
    }

    private fun attachClickListeners() {
        buttonIncrementTimesSlept.setOnClickListener {
            notify { listener ->
                listener.onIncrementTimesSleptButtonClicked()
            }
        }
        buttonIncrementTimesDidNotSlept.setOnClickListener {
            notify { listener ->
                listener.onIncrementTimesDidNotSleptButtonClicked()
            }
        }
        buttonIncrementTimesFeltTired.setOnClickListener {
            notify { listener ->
                listener.onIncrementTimesFeltTiredButtonClicked()
            }
        }
    }

    override fun bindContestant(contestant: ASMRContestant) {
        bindContestantProfilePicture(contestant.profilePicture)

        textViewContestantsDetailsName.text = contestant.name

        textViewTimesSlept.text = context.getString(
            R.string.contestant_times_slept,
            contestant.timesSlept
        )

        textViewTimesDidNotSlept.text = context.getString(
            R.string.contestant_times_did_not_slept,
            contestant.timesDidNotSlept
        )

        textViewTimesFeltTired.text = context.getString(
            R.string.contestant_times_felt_tired,
            contestant.timesFeltTired
        )
    }

    private fun bindContestantProfilePicture(profilePicture: String) {
        if (profilePicture != "")
            imageViewContestantProfilePicture.setImageURI(Uri.parse(profilePicture))
        else
            imageViewContestantProfilePicture.setImageDrawable(
                context.getDrawableResource(R.drawable.dr_contestant_profile_picture)
            )
    }
}
