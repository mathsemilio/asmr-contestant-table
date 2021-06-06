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

package br.com.mathsemilio.asmrcontestanttable.ui.dialog.bottomsheet.contestantdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import br.com.mathsemilio.asmrcontestanttable.R
import br.com.mathsemilio.asmrcontestanttable.domain.model.ASMRContestant

class ContestantDetailsViewImpl(inflater: LayoutInflater, container: ViewGroup?) :
    ContestantDetailsView() {

    private lateinit var textViewContestantsDetailsName: TextView
    private lateinit var textViewNumberTimesSlept: TextView
    private lateinit var textViewNumberTimesDidNotSlept: TextView
    private lateinit var textViewNumberTimesFeltTired: TextView
    private lateinit var buttonIncrementTimesSlept: ImageButton
    private lateinit var buttonIncrementTimesDidNotSlept: ImageButton
    private lateinit var buttonIncrementTimesFeltTired: ImageButton

    init {
        rootView = inflater.inflate(R.layout.bottom_sheet_contestant_details, container, false)

        initializeViews()

        attachClickListeners()
    }

    private fun initializeViews() {
        textViewContestantsDetailsName =
            findViewById(R.id.text_view_contestants_details_name)
        textViewNumberTimesSlept =
            findViewById(R.id.text_view_number_times_slept)
        textViewNumberTimesDidNotSlept =
            findViewById(R.id.text_view_number_times_did_not_slept)
        textViewNumberTimesFeltTired =
            findViewById(R.id.text_view_number_times_felt_tired)
        buttonIncrementTimesSlept =
            findViewById(R.id.image_button_increment_times_slept)
        buttonIncrementTimesDidNotSlept =
            findViewById(R.id.image_button_increment_times_did_not_slept)
        buttonIncrementTimesFeltTired =
            findViewById(R.id.image_button_increment_times_felt_tired)
    }

    private fun attachClickListeners() {
        buttonIncrementTimesSlept.setOnClickListener {
            notifyListener { it.onIncrementTimesSleptButtonClicked() }
        }
        buttonIncrementTimesDidNotSlept.setOnClickListener {
            notifyListener { it.onIncrementTimesDidNotSleptButtonClicked() }
        }
        buttonIncrementTimesFeltTired.setOnClickListener {
            notifyListener { it.onIncrementTimesFeltTiredButtonClicked() }
        }
    }

    override fun bindContestant(contestant: ASMRContestant) {
        textViewContestantsDetailsName.text = contestant.name
        textViewNumberTimesSlept.text = contestant.timesSlept.toString()
        textViewNumberTimesDidNotSlept.text = contestant.timesDidNotSlept.toString()
        textViewNumberTimesFeltTired.text = contestant.timesFeltTired.toString()
    }
}